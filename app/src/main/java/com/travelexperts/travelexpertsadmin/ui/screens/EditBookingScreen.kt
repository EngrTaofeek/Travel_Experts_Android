package com.travelexperts.travelexpertsadmin.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.travelexperts.travelexpertsadmin.data.api.response.Booking
import com.travelexperts.travelexpertsadmin.utils.NetworkResult
import com.travelexperts.travelexpertsadmin.viewmodels.CustomerViewModel

@Composable
fun EditBookingScreen(
    navController: NavController,
    bookingNo: String,
    viewModel: CustomerViewModel = hiltViewModel()
) {
    val bookingResult by viewModel.selectedBooking.collectAsState()
    val updateState by viewModel.updateBookingState.collectAsState()
    val context = LocalContext.current

    var booking by remember { mutableStateOf<Booking?>(null) }

    // 🔄 Fetch booking once
    LaunchedEffect(bookingNo) {
        viewModel.fetchBookingById(bookingNo)
    }

    bookingResult?.let {
        when (it) {
            is NetworkResult.Loading -> {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }

            is NetworkResult.Failure -> {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Error: ${(it as NetworkResult.Failure).message}")
                }
            }

            is NetworkResult.Success -> {
                if (booking == null) booking = it.data

                booking?.let { b ->
                    Column(Modifier.padding(16.dp).verticalScroll(rememberScrollState())) {
                        Text("Edit Booking", style = MaterialTheme.typography.headlineSmall)
                        Spacer(Modifier.height(16.dp))

                        OutlinedTextField(
                            value = b.name,
                            onValueChange = { booking = b.copy(name = it) },
                            label = { Text("Name") },
                            modifier = Modifier.fillMaxWidth()
                        )

                        OutlinedTextField(
                            value = b.destination,
                            onValueChange = { booking = b.copy(destination = it) },
                            label = { Text("Destination") },
                            modifier = Modifier.fillMaxWidth()
                        )

                        OutlinedTextField(
                            value = b.tripStart,
                            onValueChange = { booking = b.copy(tripStart = it) },
                            label = { Text("Trip Start") },
                            modifier = Modifier.fillMaxWidth()
                        )

                        OutlinedTextField(
                            value = b.tripEnd,
                            onValueChange = { booking = b.copy(tripEnd = it) },
                            label = { Text("Trip End") },
                            modifier = Modifier.fillMaxWidth()
                        )

                        OutlinedTextField(
                            value = b.travelerCount.toString(),
                            onValueChange = {
                                booking = b.copy(travelerCount = it.toIntOrNull() ?: 0)
                            },
                            label = { Text("Traveler Count") },
                            modifier = Modifier.fillMaxWidth()
                        )

                        OutlinedTextField(
                            value = b.tripTypeId,
                            onValueChange = { booking = b.copy(tripTypeId = it) },
                            label = { Text("Trip Type ID") },
                            modifier = Modifier.fillMaxWidth()
                        )

                        OutlinedTextField(
                            value = b.basePrice.toString(),
                            onValueChange = {
                                booking = b.copy(basePrice = it.toDoubleOrNull() ?: 0.0)
                            },
                            label = { Text("Base Price") },
                            modifier = Modifier.fillMaxWidth()
                        )

                        Button(
                            onClick = {
                                booking?.let { updated ->
                                    viewModel.updateBooking(updated)
                                }
                            },
                            modifier = Modifier.fillMaxWidth().padding(top = 16.dp)
                        ) {
                            Text("Save Booking")
                        }

                        LaunchedEffect(updateState) {
                            when (updateState) {
                                is NetworkResult.Success -> {
                                    Toast.makeText(context, "Booking updated", Toast.LENGTH_SHORT).show()
                                    navController.popBackStack()
                                }
                                is NetworkResult.Failure -> {
                                    Toast.makeText(context, "Update failed", Toast.LENGTH_SHORT).show()
                                }
                                else -> {}
                            }
                        }
                    }
                }
            }
        }
    }
}
