package com.travelexperts.travelexpertsadmin.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
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
import com.travelexperts.travelexpertsadmin.data.Product
import com.travelexperts.travelexpertsadmin.ui.theme.Primary

@Composable
fun ProductsScreen(navController: NavController) {
    var products by remember {
        mutableStateOf(
            listOf(
                Product(1, "Laptop"),
                Product(2, "Smartphone"),
                Product(3, "Headphones")
            )
        )
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(products) { product ->
            ProductCard(product) { updatedName ->
                products = products.map {
                    if (it.productId == product.productId) it.copy(prodName = updatedName) else it
                }
            }
        }
    }
}

@Composable
fun ProductCard(product: Product, onNameChange: (String) -> Unit) {
    var isEditMode by remember { mutableStateOf(false) }

    Card(
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
            // Default Icon
//            Image(
//                painter = painterResource(id = R.drawable.ic_product),
//                contentDescription = "Product Icon",
//                modifier = Modifier.size(50.dp)
//            )

            Icon(painter = painterResource(id = R.drawable.ic_products), contentDescription = "Product Icon", tint = Primary, modifier = Modifier.size(50.dp))

            Spacer(modifier = Modifier.width(12.dp))

            // Editable TextField
            if (isEditMode) {
                OutlinedTextField(
                    value = product.prodName,
                    onValueChange = onNameChange,
                    modifier = Modifier.weight(1f),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text)
                )
            } else {
                Text(product.prodName, style = MaterialTheme.typography.headlineSmall)
            }

            Spacer(modifier = Modifier.width(8.dp))

            // Edit Button
            IconButton(onClick = { isEditMode = !isEditMode }) {
                Icon(Icons.Default.Edit, contentDescription = "Edit", tint = MaterialTheme.colorScheme.primary)
            }
        }
    }
}
