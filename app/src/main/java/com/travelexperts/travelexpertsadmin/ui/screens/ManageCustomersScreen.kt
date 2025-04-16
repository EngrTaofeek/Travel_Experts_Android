package com.travelexperts.travelexpertsadmin.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text

import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.travelexperts.travelexpertsadmin.data.api.response.Customer
import com.travelexperts.travelexpertsadmin.datastore.UserPreferences
import com.travelexperts.travelexpertsadmin.ui.components.CustomerCard
import com.travelexperts.travelexpertsadmin.utils.NetworkResult
import com.travelexperts.travelexpertsadmin.viewmodels.CustomerViewModel

@Composable
fun ManageCustomersScreen(navController: NavController, viewModel: CustomerViewModel = hiltViewModel()) {
    val customerState by viewModel.customers.collectAsState()
    val context = LocalContext.current
    val userIdString by UserPreferences.getUserId(context).collectAsState(initial = null)

    LaunchedEffect(userIdString) {
        userIdString?.toIntOrNull()?.let { id ->
            viewModel.fetchCustomers(id)
        }
    }

    when (customerState) {
        is NetworkResult.Loading -> Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
        is NetworkResult.Failure -> Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text((customerState as NetworkResult.Failure).message, color = Color.Red)
        }

        is NetworkResult.Success -> LazyColumn(modifier = Modifier.fillMaxSize().padding(16.dp)) {
            val customers = (customerState as NetworkResult.Success<List<Customer>>).data

                items(customers.size) { index ->
                    CustomerCard(customer = customers[index], onViewBookings = {
                        navController.navigate("customerBookings/${customers[index].id}")
                    }) {
                        navController.navigate("editCustomer/${customers[index].id}")
                    }
                }


        }
    }
}

