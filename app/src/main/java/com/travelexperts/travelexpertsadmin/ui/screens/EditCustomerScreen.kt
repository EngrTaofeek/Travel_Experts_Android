package com.travelexperts.travelexpertsadmin.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
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
import androidx.navigation.NavController
import com.travelexperts.travelexpertsadmin.data.Customer

@Composable
fun EditCustomerScreen(navController: NavController, customerId: Int) {
    // Sample data - replace with real data source or ViewModel
    var customer by remember {
        mutableStateOf(
            Customer(
                customerId,
                "John", "Doe", "123 Main St", "Calgary", "AB", "T1X1Y1",
                "Canada", "4031112222", "4032223333", "john@example.com", agentId = 1
            )
        )
    }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)
        .verticalScroll(rememberScrollState())) {

        Text("Edit Customer", style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = customer.custFirstName,
            onValueChange = { customer = customer.copy(custFirstName = it) },
            label = { Text("First Name") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = customer.custLastName,
            onValueChange = { customer = customer.copy(custLastName = it) },
            label = { Text("Last Name") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = customer.custEmail,
            onValueChange = { customer = customer.copy(custEmail = it) },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = customer.custAddress,
            onValueChange = { customer = customer.copy(custAddress = it) },
            label = { Text("Address") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = customer.custCity,
            onValueChange = { customer = customer.copy(custCity = it) },
            label = { Text("City") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = customer.custProv,
            onValueChange = { customer = customer.copy(custProv = it) },
            label = { Text("Province") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = customer.custPostal,
            onValueChange = { customer = customer.copy(custPostal = it) },
            label = { Text("Postal Code") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = customer.custHomePhone ?: "",
            onValueChange = { customer = customer.copy(custHomePhone = it) },
            label = { Text("Home Phone") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = customer.custBusPhone,
            onValueChange = { customer = customer.copy(custBusPhone = it) },
            label = { Text("Business Phone") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                // TODO: Save updated customer to DB
                navController.popBackStack()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Save Changes")
        }
    }
}
