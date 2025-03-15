package com.travelexperts.travelexpertsadmin.ui.screens

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.travelexperts.travelexpertsadmin.R
import com.travelexperts.travelexpertsadmin.data.PackageData

@Composable
fun PackagesScreen(navController: NavController) {
    val packages = listOf(
        PackageData(
            1,
            "Beach Getaway",
            "2025-06-10",
            "2025-06-20",
            "Enjoy a tropical getaway",
            2000.0,
            300.0,
            "https://firebasestorage.googleapis.com/v0/b/cita-e1639.appspot.com/o/uploads%2F1599745875218.jpg?alt=media&token=57624349-bd26-4cbe-9ed7-30076c8c920e",
            "info@beachresort.com"
        ),
        PackageData(2, "Mountain Adventure", "2025-07-15", "2025-07-25", "Explore the mountains", 2500.0, 350.0, "https://firebasestorage.googleapis.com/v0/b/cita-e1639.appspot.com/o/uploads%2F1599745875218.jpg?alt=media&token=57624349-bd26-4cbe-9ed7-30076c8c920e", "info@mountaintrips.com"),
    )

    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(packages.size) { index ->
            PackageCard(packageData = packages[index], onClick = {
                navController.navigate("packageDetail/${packages[index].packageId}")
            })
        }
    }
}

@Composable
fun PackageCard(packageData: PackageData, onClick: () -> Unit) {
    Card(
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(8.dp)
    ) {
        Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
            // Package Image
            AsyncImage(
                model = packageData.imagePath,
                contentDescription = "Package Image",
                modifier = Modifier.size(80.dp)
            )

            Spacer(modifier = Modifier.width(12.dp))

            // Package Info
            Column {
                Text(packageData.pkgName, style = MaterialTheme.typography.headlineSmall)
                Text("Price: $${packageData.pkgBasePrice}", style = MaterialTheme.typography.bodyMedium)
                Text("Start Date: ${packageData.pkgStartDate}", style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}
