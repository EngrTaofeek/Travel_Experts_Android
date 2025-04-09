package com.travelexperts.travelexpertsadmin.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.travelexperts.travelexpertsadmin.R
import com.travelexperts.travelexpertsadmin.data.api.response.Product
import com.travelexperts.travelexpertsadmin.ui.theme.Primary
import com.travelexperts.travelexpertsadmin.utils.NetworkResult
import com.travelexperts.travelexpertsadmin.viewmodels.ProductViewModel

@Composable
fun ProductsScreen(navController: NavController, viewModel: ProductViewModel = hiltViewModel()) {
    val productState by viewModel.productState.collectAsState()
    val updateState by viewModel.updateState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(snackbarHost = { SnackbarHost(snackbarHostState) }) { padding ->
        Box(modifier = Modifier.padding(padding)) {
            when (productState) {
                is NetworkResult.Loading -> Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) { CircularProgressIndicator() }
                is NetworkResult.Success -> {
                    val products = (productState as NetworkResult.Success<List<Product>>).data
                    ProductsList(products, viewModel)
                }
                is NetworkResult.Failure -> Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = (productState as NetworkResult.Failure).message, color = Color.Red)
                }
            }

            updateState?.let {
                LaunchedEffect(it) {
                    when (it) {
                        is NetworkResult.Loading -> snackbarHostState.showSnackbar("Updating...")
                        is NetworkResult.Success -> snackbarHostState.showSnackbar("Updated: ${it.data.prodname}")
                        is NetworkResult.Failure -> snackbarHostState.showSnackbar("Update Failed: ${it.message}")
                    }
                }
            }
        }
    }
}

@Composable
fun ProductsList(products: List<Product>, viewModel: ProductViewModel) {
    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(products) { product ->
            ProductCard(product = product) { updatedProduct ->
                viewModel.updateProduct(updatedProduct)
            }
        }
    }
}

@Composable
fun ProductCard(product: Product, onNameChange: (Product) -> Unit) {
    var isEditMode by remember { mutableStateOf(false) }
    var editedName by remember { mutableStateOf(product.prodname) }

    Card(
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_products),
                contentDescription = "Product Icon",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(50.dp).padding(end = 8.dp)
            )

            if (isEditMode) {
                OutlinedTextField(
                    value = editedName ?: "",
                    onValueChange = { editedName = it },
                    modifier = Modifier
                        .weight(1f)
                        .heightIn(min = 56.dp, max = 120.dp),
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text),
                    maxLines = 3
                )
            } else {
                Text(
                    text = product.prodname ?: "",
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 8.dp),
                    maxLines = 2
                )
            }

            IconButton(
                onClick = {
                    if (isEditMode) {
                        onNameChange(product.copy(prodname = editedName))
                    }
                    isEditMode = !isEditMode
                },
                modifier = Modifier.align(Alignment.Top)
            ) {
                Icon(if (!isEditMode) Icons.Default.Edit else Icons.Default.CheckCircle, contentDescription = "Edit", tint = MaterialTheme.colorScheme.primary)
            }
        }
    }
}
