package com.travelexperts.travelexpertsadmin.viewmodels

import android.Manifest
import android.app.Application
import android.content.Context
import android.util.Log
import androidx.annotation.RequiresPermission
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.AutocompletePrediction
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.model.RectangularBounds
import com.google.android.libraries.places.api.model.TypeFilter
import com.google.android.libraries.places.api.net.FetchPlaceRequest
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest
import com.google.android.libraries.places.api.net.FindCurrentPlaceRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ExploreViewModel @Inject constructor(
    private val app: Application
) : AndroidViewModel(app) {

    private val _selectedPlace = mutableStateOf<Place?>(null)
    val selectedPlace: State<Place?> = _selectedPlace

    private val _nearbyPlaces = mutableStateListOf<Place>()
    val nearbyPlaces: List<Place> = _nearbyPlaces

    fun onPlaceSelected(place: Place) {
        _selectedPlace.value = place
        // Fetch nearby attractions after selecting a place
        fetchNearbyAttractions(place.latLng)
    }

    private fun fetchNearbyAttractions(latLng: LatLng?) {
        latLng ?: return
        _nearbyPlaces.clear()

        val context = getApplication<Application>().applicationContext
        val request = FindCurrentPlaceRequest.newInstance(
            listOf(Place.Field.NAME, Place.Field.LAT_LNG, Place.Field.ADDRESS, Place.Field.RATING, Place.Field.PHOTO_METADATAS)
        )

        val placesClient = Places.createClient(context)

        val bias = RectangularBounds.newInstance(
            LatLng(latLng.latitude - 0.1, latLng.longitude - 0.1),
            LatLng(latLng.latitude + 0.1, latLng.longitude + 0.1)
        )

        val nearbyRequest = FindAutocompletePredictionsRequest.builder()
            .setLocationBias(bias)
            .setQuery("tourist attraction")
            .build()

        placesClient.findAutocompletePredictions(nearbyRequest)
            .addOnSuccessListener { response ->
                response.autocompletePredictions.forEach { prediction ->
                    val placeId = prediction.placeId
                    val placeRequest = FetchPlaceRequest.newInstance(
                        placeId,
                        listOf(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG, Place.Field.ADDRESS, Place.Field.RATING)
                    )
                    placesClient.fetchPlace(placeRequest)
                        .addOnSuccessListener { fetchedPlace ->
                            _nearbyPlaces.add(fetchedPlace.place)
                        }
                }
            }
            .addOnFailureListener {
                Log.e("ExploreViewModel", "Failed to fetch nearby places", it)
            }
    }
}

