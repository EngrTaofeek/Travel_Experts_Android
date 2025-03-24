package com.travelexperts.travelexpertsadmin.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.*
import androidx.compose.ui.text.input.KeyboardType
import androidx.navigation.NavController
import com.travelexperts.travelexpertsadmin.data.Booking

@Composable
fun EditBookingScreen(navController: NavController, bookingId: Int) {
    // Sample data - replace with real fetch
    var booking by remember {
        mutableStateOf(
            Booking(
                bookingId,
                "2025-06-01",
                "BK101",
                2.0,
                customerId = 1,
                tripTypeId = "L",
                packageId = 1
            )
        )
    }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)
        .verticalScroll(rememberScrollState())) {

        Text("Edit Booking", style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = booking.bookingNo ?: "",
            onValueChange = { booking = booking.copy(bookingNo = it) },
            label = { Text("Booking No") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = booking.bookingDate,
            onValueChange = { booking = booking.copy(bookingDate = it) },
            label = { Text("Booking Date (YYYY-MM-DD)") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = booking.travelerCount.toString(),
            onValueChange = { booking = booking.copy(travelerCount = it.toDoubleOrNull() ?: 1.0) },
            label = { Text("Traveler Count") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = booking.tripTypeId ?: "",
            onValueChange = { booking = booking.copy(tripTypeId = it) },
            label = { Text("Trip Type ID") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = booking.packageId?.toString() ?: "",
            onValueChange = { booking = booking.copy(packageId = it.toIntOrNull()) },
            label = { Text("Package ID") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                // TODO: Save updated booking to DB
                navController.popBackStack()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Save Changes")
        }
    }
}
