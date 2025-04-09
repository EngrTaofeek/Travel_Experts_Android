package com.travelexperts.travelexpertsadmin.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.travelexperts.travelexpertsadmin.utils.NetworkResult
import com.travelexperts.travelexpertsadmin.viewmodels.CustomerViewModel


@Composable
fun EditCustomerScreen(navController: NavController, customerId: Int, viewModel: CustomerViewModel = hiltViewModel()) {
    val state by viewModel.selectedCustomer.collectAsState()
    val updateState by viewModel.updateCustomerState.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(customerId) {
        viewModel.fetchCustomer(customerId)
    }

    state?.let {
        when (it) {
            is NetworkResult.Loading -> CircularProgressIndicator()
            is NetworkResult.Failure -> Text("Error: ${(it as NetworkResult.Failure).message}")
            is NetworkResult.Success -> {
                var customer by remember { mutableStateOf(it.data) }

                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .verticalScroll(rememberScrollState())
                ) {
                    Text("Edit Customer", style = MaterialTheme.typography.headlineSmall)
                    Spacer(Modifier.height(16.dp))

                    OutlinedTextField(value = customer.custfirstname, onValueChange = {
                        customer = customer.copy(custfirstname = it)
                    }, label = { Text("First Name") }, modifier = Modifier.fillMaxWidth())

                    OutlinedTextField(value = customer.custlastname, onValueChange = {
                        customer = customer.copy(custlastname = it)
                    }, label = { Text("Last Name") }, modifier = Modifier.fillMaxWidth())

                    OutlinedTextField(value = customer.custemail, onValueChange = {
                        customer = customer.copy(custemail = it)
                    }, label = { Text("Email") }, modifier = Modifier.fillMaxWidth())

                    OutlinedTextField(value = customer.custaddress, onValueChange = {
                        customer = customer.copy(custaddress = it)
                    }, label = { Text("Address") }, modifier = Modifier.fillMaxWidth())

                    OutlinedTextField(value = customer.custcity, onValueChange = {
                        customer = customer.copy(custcity = it)
                    }, label = { Text("City") }, modifier = Modifier.fillMaxWidth())

                    OutlinedTextField(value = customer.custprov, onValueChange = {
                        customer = customer.copy(custprov = it)
                    }, label = { Text("Province") }, modifier = Modifier.fillMaxWidth())

                    OutlinedTextField(value = customer.custpostal, onValueChange = {
                        customer = customer.copy(custpostal = it)
                    }, label = { Text("Postal Code") }, modifier = Modifier.fillMaxWidth())

                    OutlinedTextField(value = customer.custhomephone ?: "", onValueChange = {
                        customer = customer.copy(custhomephone = it)
                    }, label = { Text("Home Phone") }, modifier = Modifier.fillMaxWidth())

                    OutlinedTextField(value = customer.custbusphone, onValueChange = {
                        customer = customer.copy(custbusphone = it)
                    }, label = { Text("Business Phone") }, modifier = Modifier.fillMaxWidth())

                    Spacer(Modifier.height(16.dp))

                    Button(onClick = {
                        viewModel.updateCustomer(customer)
                    }, modifier = Modifier.fillMaxWidth()) {
                        Text("Save")
                    }
                }

                // Show result of update
                LaunchedEffect(updateState) {
                    when (updateState) {
                        is NetworkResult.Success -> Toast.makeText(context, "Customer updated", Toast.LENGTH_SHORT).show()
                        is NetworkResult.Failure -> Toast.makeText(context, "Update failed", Toast.LENGTH_SHORT).show()
                        else -> {}
                    }
                }
            }
        }
    }
}
