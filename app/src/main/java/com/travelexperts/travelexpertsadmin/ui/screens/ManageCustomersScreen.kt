package com.travelexperts.travelexpertsadmin.ui.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.travelexperts.travelexpertsadmin.data.Customer
import com.travelexperts.travelexpertsadmin.ui.components.CustomerCard

@Composable
fun ManageCustomersScreen(navController: NavController) {
    var customers = listOf(
                Customer(1, "John", "Doe", "123 Main St", "New York", "NY", "10001", "USA", "1234567890", "0987654321", "john@example.com", 101),
                Customer(2, "Jane", "Smith", "456 Elm St", "Los Angeles", "CA", "90001", "USA", null, "1231231234", "jane@example.com", 102)
            )

    LazyColumn(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        items(customers.size) { index ->
            CustomerCard(customer = customers[index], onViewBookings = {
                navController.navigate("customerBookings/${customers[index].customerId}")
            }) {
                navController.navigate("editCustomer/${customers[index].customerId}")
            }
        }
    }
}
