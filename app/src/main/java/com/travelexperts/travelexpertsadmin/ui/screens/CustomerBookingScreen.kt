package com.travelexperts.travelexpertsadmin.ui.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.travelexperts.travelexpertsadmin.ui.components.BookingCard
import com.travelexperts.travelexpertsadmin.utils.NetworkResult
import com.travelexperts.travelexpertsadmin.viewmodels.CustomerViewModel

@Composable
fun CustomerBookingsScreen(navController: NavController, customerId: Int, viewModel: CustomerViewModel = hiltViewModel()) {
    val bookings by viewModel.bookings.collectAsState()

    LaunchedEffect(customerId) {
        viewModel.fetchBookings(customerId)
    }

    bookings?.let {
        when (it) {
            is NetworkResult.Loading -> CircularProgressIndicator()
            is NetworkResult.Failure -> Text("Error: ${(it as NetworkResult.Failure).message}")
            is NetworkResult.Success -> {

                val data = it.data
                LazyColumn(modifier = Modifier.fillMaxSize().padding(16.dp)) {
                    items(data.size) { index ->
                        BookingCard(booking = data[index]) {
                            navController.navigate("editBooking/${data[index].bookingNo}")
                            // You can make booking fields editable here if needed
                        }
                    }
                }

            }
        }
    }
}

