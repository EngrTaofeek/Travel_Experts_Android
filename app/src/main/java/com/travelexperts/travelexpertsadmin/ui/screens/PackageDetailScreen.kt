package com.travelexperts.travelexpertsadmin.ui.screens

import android.net.Uri
import android.util.Log
import android.widget.Toast
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.travelexperts.travelexpertsadmin.R
import com.travelexperts.travelexpertsadmin.data.api.response.PackageData
import com.travelexperts.travelexpertsadmin.di.BASE_URL

import com.travelexperts.travelexpertsadmin.ui.components.DatePickerField
import com.travelexperts.travelexpertsadmin.utils.NetworkResult
import com.travelexperts.travelexpertsadmin.viewmodels.PackageViewModel
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun PackageDetailScreen(
    navController: NavController,
    packageId: Int,
    viewModel: PackageViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val detailState by viewModel.selectedPackage.collectAsState()
    val updateState by viewModel.updateState.collectAsState()
    var isEditMode by remember { mutableStateOf(false) }

    var imageUri by remember { mutableStateOf<Uri?>(null) }

    val galleryLauncher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let {
            imageUri = it // set local uri for instant preview
            viewModel.uploadImage(packageId, it, context.contentResolver)
        }
    }


    LaunchedEffect(Unit) {
        viewModel.fetchPackageById(packageId)
    }

    LaunchedEffect(updateState) {
        when (updateState) {
            is NetworkResult.Success -> Toast.makeText(context, "Package Updated", Toast.LENGTH_SHORT).show()
            is NetworkResult.Failure -> Toast.makeText(context, "Error updating", Toast.LENGTH_SHORT).show()
            else -> Unit
        }
    }

    when (detailState) {
        is NetworkResult.Loading -> Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }

        is NetworkResult.Success -> {
            val pkg = (detailState as NetworkResult.Success<PackageData>).data
            var localPkg by remember { mutableStateOf(pkg) }

            Column(Modifier
                .padding(16.dp)
                .verticalScroll(rememberScrollState())) {

                Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    val displayedImage = imageUri?.toString()
                        ?: localPkg.imageUrl?.let { if (it.startsWith("/")) "${BASE_URL.dropLast(1)}$it" else it }



                    AsyncImage(
                        model = displayedImage ?: R.drawable.default_package,
                        contentDescription = null,
                        modifier = Modifier
                            .size(150.dp)
                            .clip(RoundedCornerShape(8.dp)),
                        contentScale = ContentScale.Crop
                    )
                    if (isEditMode) {
                        IconButton(onClick = { galleryLauncher.launch("image/*") }) {
                            Icon(Icons.Default.Edit, contentDescription = "Change Image", tint = MaterialTheme.colorScheme.primary)
                        }
                    }
                }

                Spacer(Modifier.height(16.dp))

                OutlinedTextField(
                    value = localPkg.pkgname,
                    onValueChange = { localPkg = localPkg.copy(pkgname = it) },
                    label = { Text("Package Name") },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = isEditMode
                )

                OutlinedTextField(
                    value = localPkg.pkgdesc ?: "",
                    onValueChange = { localPkg = localPkg.copy(pkgdesc = it) },
                    label = { Text("Description") },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = isEditMode
                )

                OutlinedTextField(
                    value = localPkg.pkgbaseprice.toString(),
                    onValueChange = { localPkg = localPkg.copy(pkgbaseprice = it.toDoubleOrNull() ?: 0.0) },
                    label = { Text("Base Price") },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = isEditMode
                )

                OutlinedTextField(
                    value = localPkg.pkgagencycommission.toString(),
                    onValueChange = { localPkg = localPkg.copy(pkgagencycommission = it.toDoubleOrNull() ?: 0.0) },
                    label = { Text("Agency Commission") },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = isEditMode
                )

                DatePickerField(
                    label = "Start Date",
                    date = localPkg.pkgstartdate.substring(0, 10),
                    isEnabled = isEditMode
                ) { selected ->
                    localPkg = localPkg.copy(pkgstartdate = selected)
                }

                DatePickerField(
                    label = "End Date",
                    date = localPkg.pkgenddate.substring(0, 10),
                    isEnabled = isEditMode
                ) { selected ->
                    localPkg = localPkg.copy(pkgenddate = selected)
                }

                Spacer(Modifier.height(16.dp))

                Button(
                    onClick = {
                        if (isEditMode) viewModel.updatePackage(localPkg)
                        isEditMode = !isEditMode
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(if (isEditMode) "Save Changes" else "Edit")
                }

                if (!localPkg.reviews.isNullOrEmpty()) {
                    Spacer(Modifier.height(16.dp))
                    Text("Reviews:", style = MaterialTheme.typography.titleMedium)
                    localPkg.reviews.forEach {
                        Text("- $it", style = MaterialTheme.typography.bodyMedium)
                    }
                }
            }
        }

        is NetworkResult.Failure -> Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(text = (detailState as NetworkResult.Failure).message, color = Color.Red)
        }

        null -> Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    }
}
