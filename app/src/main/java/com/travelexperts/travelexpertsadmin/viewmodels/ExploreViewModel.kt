package com.travelexperts.travelexpertsadmin.viewmodels


import android.app.Application
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import com.android.volley.BuildConfig
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.FetchPlaceRequest
import com.google.android.libraries.places.api.net.PlacesClient
import com.google.android.gms.maps.model.LatLngBounds
import com.travelexperts.travelexpertsadmin.di.MAPS_API_KEY
import dagger.hilt.android.lifecycle.HiltViewModel
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
import javax.inject.Inject
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import java.io.IOException



@HiltViewModel
class ExploreViewModel @Inject constructor(
    private val app: Application
) : AndroidViewModel(app) {

    private val _selectedPlace = mutableStateOf<Place?>(null)
    val selectedPlace: State<Place?> = _selectedPlace

    private val _nearbyPlaces = mutableStateListOf<NearbyPlace>()
    val nearbyPlaces: List<NearbyPlace> = _nearbyPlaces

    fun onPlaceSelected(place: Place) {
        _selectedPlace.value = place
        fetchNearbyAttractions(place.latLng)
    }

    private fun fetchNearbyAttractions(latLng: LatLng?) {
        latLng ?: return
        _nearbyPlaces.clear()

        val url = HttpUrl.Builder()
            .scheme("https")
            .host("maps.googleapis.com")
            .addPathSegments("maps/api/place/nearbysearch/json")
            .addQueryParameter("location", "${latLng.latitude},${latLng.longitude}")
            .addQueryParameter("radius", "50000") // 50km
            .addQueryParameter("type", "tourist_attraction")
            .addQueryParameter("key", MAPS_API_KEY)
            .build()

        val request = Request.Builder().url(url).build()
        val client = OkHttpClient()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("ExploreViewModel", "Nearby attractions fetch failed", e)
            }

            override fun onResponse(call: Call, response: Response) {
                response.body?.string()?.let { jsonString ->
                    val json = JSONObject(jsonString)
                    val results = json.getJSONArray("results")
                    for (i in 0 until results.length()) {
                        val item = results.getJSONObject(i)
                        val name = item.optString("name")
                        val address = item.optString("vicinity")
                        val rating = item.optDouble("rating", -1.0)
                        val photos = item.optJSONArray("photos")
                        val photoReference = photos?.optJSONObject(0)?.optString("photo_reference")

                        val imageUrl = photoReference?.let {
                            "https://maps.googleapis.com/maps/api/place/photo" +
                                    "?maxwidth=400&photoreference=$it&key=${MAPS_API_KEY}"
                        }

                        val nearbyPlace = NearbyPlace(
                            name = name,
                            address = address,
                            rating = if (rating != -1.0) rating else null,
                            imageUrl = imageUrl
                        )
                        _nearbyPlaces.add(nearbyPlace)
                    }
                }
            }
        })
    }
}

data class NearbyPlace(
    val name: String,
    val address: String?,
    val rating: Double?,
    val imageUrl: String?
)

