package com.travelexperts.travelexpertsadmin.ui.screens


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.travelexperts.travelexpertsadmin.R
import com.travelexperts.travelexpertsadmin.data.Supplier

@Composable
fun SuppliersScreen(navController: NavController) {
    var suppliers by remember {
        mutableStateOf(
            listOf(
                Supplier(1, "Global Supplies"),
                Supplier(2, "Tech Solutions"),
                Supplier(3, "Luxury Brands")
            )
        )
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(suppliers) { supplier ->
            SupplierCard(supplier) { updatedName ->
                suppliers = suppliers.map {
                    if (it.supplierId == supplier.supplierId) it.copy(supName = updatedName) else it
                }
            }
        }
    }
}

@Composable
fun SupplierCard(supplier: Supplier, onNameChange: (String) -> Unit) {
    var isEditMode by remember { mutableStateOf(false) }

    Card(
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
            // Default Icon
            Image(
                painter = painterResource(id = R.drawable.mdi_truck),
                contentDescription = "Supplier Icon",
                modifier = Modifier.size(50.dp)
            )

            Spacer(modifier = Modifier.width(12.dp))

            // Editable TextField
            if (isEditMode) {
                OutlinedTextField(
                    value = supplier.supName,
                    onValueChange = onNameChange,
                    modifier = Modifier.weight(1f),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text)
                )
            } else {
                Text(supplier.supName, style = MaterialTheme.typography.headlineSmall)
            }

            Spacer(modifier = Modifier.width(8.dp))

            // Edit Button
            IconButton(onClick = { isEditMode = !isEditMode }) {
                Icon(Icons.Default.Edit, contentDescription = "Edit", tint = MaterialTheme.colorScheme.primary)
            }
        }
    }
}
