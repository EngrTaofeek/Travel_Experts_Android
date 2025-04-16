package com.travelexperts.travelexpertsadmin.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.travelexperts.travelexpertsadmin.ui.components.EmailTextField
import com.travelexperts.travelexpertsadmin.ui.components.OutlineButton
import com.travelexperts.travelexpertsadmin.ui.components.PasswordTextField
import com.travelexperts.travelexpertsadmin.ui.components.SolidButton
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.travelexperts.travelexpertsadmin.R
import com.travelexperts.travelexpertsadmin.data.api.response.PackageData
import com.travelexperts.travelexpertsadmin.data.api.response.Product
import com.travelexperts.travelexpertsadmin.di.BASE_URL
import com.travelexperts.travelexpertsadmin.utils.NetworkResult
import com.travelexperts.travelexpertsadmin.viewmodels.PackageViewModel
import com.travelexperts.travelexpertsadmin.viewmodels.ProductViewModel

@Composable
fun PackagesScreen(
    navController: NavController,
    viewModel: PackageViewModel = hiltViewModel()
) {
    val state by viewModel.packageListState.collectAsState()

    when (state) {
        is NetworkResult.Loading -> Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }

        is NetworkResult.Success -> {
            val packages = (state as NetworkResult.Success<List<PackageData>>).data
            PackagesList(packages, navController)

        }

        is NetworkResult.Failure -> Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(text = (state as NetworkResult.Failure).message, color = Color.Red)
        }
    }
}
@Composable
fun PackagesList(packages: List<PackageData>, navController: NavController) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        items(packages) { pkg ->
            Log.i("PackagesList", "${BASE_URL.dropLast(1)}${pkg.imageUrl}")
            PackageCard(pkg) {
                navController.navigate("packageDetail/${pkg.id}")
            }
        }
    }
}

@Composable
fun PackageCard(packageData: PackageData, onClick: () -> Unit) {
    Card(
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
    ) {
        Row(Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
            AsyncImage(
                model = packageData.imageUrl?.let { if (it.startsWith("/")) "${BASE_URL.dropLast(1)}$it" else it }
                    ?: R.drawable.default_package,
                contentDescription = "Package Image",
                modifier = Modifier.size(80.dp)
            )
            Spacer(Modifier.width(12.dp))
            Column {
                Text(packageData.pkgname, style = MaterialTheme.typography.headlineSmall)
                Text("Price: $${packageData.pkgbaseprice}")
                Text("Start: ${packageData.pkgstartdate.substring(0, 10)}")
            }
        }
    }
}
