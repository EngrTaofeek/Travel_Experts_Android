package com.travelexperts.travelexpertsadmin.ui.screens

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.travelexperts.travelexpertsadmin.R
import com.travelexperts.travelexpertsadmin.data.PackageData

import android.app.DatePickerDialog
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.travelexperts.travelexpertsadmin.ui.components.DatePickerField
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun PackageDetailScreen(navController: NavController, packageId: Int) {
    var isEditMode by remember { mutableStateOf(false) }
    var packageImageUri by remember { mutableStateOf<Uri?>(null) }
    val context = LocalContext.current

    // Date Formatter
    val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

    // Image Picker
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri -> if (uri != null) packageImageUri = uri }
    )

    // Sample Package Data (Replace with API Call)
    var packageData by remember {
        mutableStateOf(
            PackageData(
                packageId, "Beach Getaway", "2025-06-10", "2025-06-20",
                "Enjoy a tropical getaway with all-inclusive services.",
                2000.0, 300.0,
                "https://firebasestorage.googleapis.com/v0/b/cita-e1639.appspot.com/o/uploads%2F1599745875218.jpg?alt=media&token=57624349-bd26-4cbe-9ed7-30076c8c920e",
                "info@beachresort.com"
            )
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Top
    ) {
        // 📌 Package Image with Edit Icon
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            AsyncImage(
                model = packageImageUri ?: packageData.imagePath,
                contentDescription = "Package Image",
                placeholder = painterResource(id = R.drawable.onboarding),
                modifier = Modifier
                    .size(150.dp)
                    .clip(RoundedCornerShape(8.dp))
            )

            if (isEditMode) {
                IconButton(
                    onClick = { galleryLauncher.launch("image/*") },
                    modifier = Modifier
                        .size(40.dp)
                        .align(Alignment.BottomEnd)
                        .background(MaterialTheme.colorScheme.primary, shape = RoundedCornerShape(20.dp))
                ) {
                    Icon(Icons.Default.Edit, contentDescription = "Edit Image", tint = Color.White)
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 📌 Package Name
        OutlinedTextField(
            value = packageData.pkgName,
            onValueChange = { packageData = packageData.copy(pkgName = it) },
            label = { Text("Package Name") },
            enabled = isEditMode,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        // 📌 Package Description
        OutlinedTextField(
            value = packageData.pkgDesc,
            onValueChange = { packageData = packageData.copy(pkgDesc = it) },
            label = { Text("Description") },
            enabled = isEditMode,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        // 📌 Package Base Price
        OutlinedTextField(
            value = packageData.pkgBasePrice.toString(),
            onValueChange = { packageData = packageData.copy(pkgBasePrice = it.toDoubleOrNull() ?: 0.0) },
            label = { Text("Base Price") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            enabled = isEditMode,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        // 📌 Package Agency Commission
        OutlinedTextField(
            value = packageData.pkgAgencyCommission.toString(),
            onValueChange = { packageData = packageData.copy(pkgAgencyCommission = it.toDoubleOrNull() ?: 0.0) },
            label = { Text("Agency Commission") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            enabled = isEditMode,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        // 📌 Package Email
        OutlinedTextField(
            value = packageData.email,
            onValueChange = { packageData = packageData.copy(email = it) },
            label = { Text("Email") },
            enabled = isEditMode,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        // 📌 Package Start Date Picker
        DatePickerField(
            label = "Start Date",
            date = packageData.pkgStartDate,
            isEnabled = isEditMode
        ) { selectedDate ->
            packageData = packageData.copy(pkgStartDate = selectedDate)
        }

        Spacer(modifier = Modifier.height(8.dp))

        // 📌 Package End Date Picker
        DatePickerField(
            label = "End Date",
            date = packageData.pkgEndDate,
            isEnabled = isEditMode
        ) { selectedDate ->
            packageData = packageData.copy(pkgEndDate = selectedDate)
        }

        Spacer(modifier = Modifier.height(16.dp))

        //  Toggle Edit Mode
        Button(
            onClick = { isEditMode = !isEditMode },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(if (isEditMode) "Save Changes" else "Edit Package")
        }
    }
}
