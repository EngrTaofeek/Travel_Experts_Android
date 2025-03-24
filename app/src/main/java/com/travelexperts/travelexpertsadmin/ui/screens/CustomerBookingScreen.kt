package com.travelexperts.travelexpertsadmin.ui.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.travelexperts.travelexpertsadmin.data.Booking
import com.travelexperts.travelexpertsadmin.ui.components.BookingCard

@Composable
fun CustomerBookingsScreen(navController: NavController, customerId: Int) {
    var bookings = listOf(
                Booking(1, "2025-06-01", "BK101", 2.0, customerId, "L", 1),
                Booking(2, "2025-07-10", "BK102", 1.0, customerId, "A", 2)
            )


    LazyColumn(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        items(bookings.size) { index ->
            BookingCard(booking = bookings[index]) {
                navController.navigate("editBooking/${bookings[index].bookingId}")
                // You can make booking fields editable here if needed
            }
        }
    }
}
