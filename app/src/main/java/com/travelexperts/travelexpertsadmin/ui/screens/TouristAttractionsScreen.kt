package com.travelexperts.travelexpertsadmin.ui.screens

import android.Manifest
import android.app.Activity
import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material3.*

import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.AutocompletePrediction
import com.google.android.libraries.places.api.model.AutocompleteSessionToken
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.FetchPlaceRequest
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.AutocompleteActivity
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.rememberCameraPositionState
import com.travelexperts.travelexpertsadmin.viewmodels.ExploreViewModel

@Composable
fun TouristAttractionsScreen(
    navController: NavController,
    viewModel: ExploreViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val placesClient = remember { Places.createClient(context) }

    val autocompleteSessionToken = remember { AutocompleteSessionToken.newInstance() }
    var query by remember { mutableStateOf("") }
    var predictions by remember { mutableStateOf<List<AutocompletePrediction>>(emptyList()) }

    val selectedPlace by viewModel.selectedPlace
    val nearbyPlaces = viewModel.nearbyPlaces

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {

        OutlinedTextField(
            value = query,
            onValueChange = {
                query = it
                val request = FindAutocompletePredictionsRequest.builder()
                    .setSessionToken(autocompleteSessionToken)
                    .setQuery(it)
                    .build()

                placesClient.findAutocompletePredictions(request)
                    .addOnSuccessListener { response ->
                        predictions = response.autocompletePredictions
                    }
            },
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Search city or attraction") }
        )

        LazyColumn {
            items(predictions.size) { prediction ->
                Text(
                    text = predictions[prediction].getFullText(null).toString(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            query = predictions[prediction].getFullText(null).toString()
                            predictions = emptyList()
                            val request = FetchPlaceRequest.newInstance(
                                predictions[prediction].placeId,
                                listOf(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG, Place.Field.ADDRESS)
                            )
                            placesClient.fetchPlace(request)
                                .addOnSuccessListener { result ->
                                    viewModel.onPlaceSelected(result.place)
                                }
                        }
                        .padding(vertical = 8.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (nearbyPlaces.isNotEmpty()) {
            Text("Nearby Tourist Attractions:", style = MaterialTheme.typography.titleMedium)

            LazyColumn {
                items(nearbyPlaces.size) { place ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        elevation = CardDefaults.cardElevation(4.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(nearbyPlaces[place].name ?: "Unknown", style = MaterialTheme.typography.titleMedium)
                            nearbyPlaces[place].address?.let { Text("📍 $it", style = MaterialTheme.typography.bodySmall) }
                            nearbyPlaces[place].latLng?.let {
                                Text("🧭 (${it.latitude}, ${it.longitude})", style = MaterialTheme.typography.bodySmall)
                            }
                            nearbyPlaces[place].rating?.let {
                                Text("⭐ Rating: $it", style = MaterialTheme.typography.bodySmall)
                            }
                        }
                    }
                }
            }
        } else {
            Text("No nearby attractions yet. Start by searching a place.", modifier = Modifier.padding(top = 24.dp))
        }
    }
}
