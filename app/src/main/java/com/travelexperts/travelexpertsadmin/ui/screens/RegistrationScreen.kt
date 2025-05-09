package com.travelexperts.travelexpertsadmin.ui.screens


import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.*
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.travelexperts.travelexpertsadmin.R
import com.travelexperts.travelexpertsadmin.data.api.response.Agency
import com.travelexperts.travelexpertsadmin.ui.components.CustomLoader
import com.travelexperts.travelexpertsadmin.ui.components.DropdownMenuComponent
import com.travelexperts.travelexpertsadmin.ui.components.EmailTextField
import com.travelexperts.travelexpertsadmin.ui.components.OutlineButton
import com.travelexperts.travelexpertsadmin.ui.components.PasswordTextField
import com.travelexperts.travelexpertsadmin.ui.components.SolidButton
import com.travelexperts.travelexpertsadmin.ui.navigation.Routes
import com.travelexperts.travelexpertsadmin.utils.NetworkResult
import com.travelexperts.travelexpertsadmin.viewmodels.AgencyViewModel
import com.travelexperts.travelexpertsadmin.viewmodels.AgentViewModel
import com.travelexperts.travelexpertsadmin.viewmodels.AuthenticationViewModel
import java.io.File

@Composable
fun RegisterScreen(
    navController: NavController,
    viewModel: AuthenticationViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val registerState by viewModel.registerState.collectAsState()
    val agencyViewModel: AgencyViewModel = hiltViewModel()
    val agencyState by agencyViewModel.agencies.collectAsState()
    val agencies = when (agencyState) {
        is NetworkResult.Success -> (agencyState as NetworkResult.Success<List<Agency>>).data
        else -> emptyList()
    }

    var firstName by remember { mutableStateOf("") }
    var middleInitial by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var selectedPosition by remember { mutableStateOf("") }
    var selectedAgencyName by remember { mutableStateOf("") }
    var selectedAgencyId by remember { mutableStateOf(0) }


    val positions = listOf("Agent", "Senior Agent", "Support Agent")

    var isPasswordVisible by remember { mutableStateOf(false) }
    var profilePhotoUri by remember { mutableStateOf<Uri?>(null) }

    val galleryLauncher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) {
        profilePhotoUri = it
    }

    var tempImageUri by remember { mutableStateOf<Uri?>(null) }

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { success ->
        if (success) {
            tempImageUri?.let { profilePhotoUri = it }
        }
    }


    val snackbarHostState = remember { SnackbarHostState() }
    val loginState by viewModel.loginState.collectAsState()
    // 🎯 React to login state underground without user knowing
    LaunchedEffect(loginState) {
        when (loginState) {
            is NetworkResult.Loading -> { }
            is NetworkResult.Success -> {
                navController.navigate(Routes.HOME) {
                    popUpTo(Routes.LOGIN) { inclusive = true }
                }
                snackbarHostState.showSnackbar("Login successful")
            }
            is NetworkResult.Failure -> {
                snackbarHostState.showSnackbar(
                    (loginState as NetworkResult.Failure).message
                )
                Log.i("teekay",(loginState as NetworkResult.Failure).message)
            }
            else -> Unit
        }

    }

    LaunchedEffect(registerState) {
        when (registerState) {
            is NetworkResult.Loading -> snackbarHostState.showSnackbar("Registering agent...")
            is NetworkResult.Success -> snackbarHostState.showSnackbar("Agent registered successfully")
            is NetworkResult.Failure -> snackbarHostState.showSnackbar("Registration failed: ${(registerState as NetworkResult.Failure).message}")
            null -> {}
        }
    }

    Scaffold(snackbarHost = { SnackbarHost(snackbarHostState) }) { padding ->
        Box(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .padding(padding)
                    .padding(24.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Register Agent", style = MaterialTheme.typography.headlineMedium)
                Spacer(Modifier.height(16.dp))

                Box(
                    modifier = Modifier
                        .size(120.dp)
                        .clickable { galleryLauncher.launch("image/*") },
                    contentAlignment = Alignment.Center
                ) {
                    if (profilePhotoUri != null) {
                        AsyncImage(
                            model = profilePhotoUri,
                            contentDescription = "Profile Picture",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(120.dp)
                                .clip(CircleShape)
                                .border(2.dp, MaterialTheme.colorScheme.primary, CircleShape)
                        )
                    } else {
                        Image(
                            painter = painterResource(R.drawable.baseline_person_24),
                            contentDescription = "Placeholder",
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                }
                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    TextButton(onClick = { galleryLauncher.launch("image/*") }) {
                        Text("Pick from Gallery")
                    }
                    TextButton(onClick = {
                        val uri = createTempImageUri(context)
                        tempImageUri = uri
                        cameraLauncher.launch(uri)
                    }) {
                        Text("Take Photo")
                    }

                }
                Spacer(Modifier.height(16.dp))

                // Input Fields
                OutlinedTextField(
                    value = firstName,
                    onValueChange = { firstName = it },
                    label = { Text("First Name") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = middleInitial,
                    onValueChange = { middleInitial = it },
                    label = { Text("Middle Initial") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = lastName,
                    onValueChange = { lastName = it },
                    label = { Text("Last Name") },
                    modifier = Modifier.fillMaxWidth()
                )
                if (loginState is NetworkResult.Loading || registerState is NetworkResult.Loading) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
                OutlinedTextField(
                    value = phoneNumber,
                    onValueChange = { if (it.all(Char::isDigit)) phoneNumber = it },
                    label = { Text("Phone Number") },
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Phone),
                    modifier = Modifier.fillMaxWidth()
                )

                EmailTextField(email = email, onEmailChange = { email = it })

                DropdownMenuComponent(
                    label = "Position",
                    items = positions,
                    selectedItem = selectedPosition,
                    enabled = true,
                    onItemSelected = { selectedPosition = it }
                )

                DropdownMenuComponent(
                    label = "Agency",
                    items = agencies.map { "${it.agncycity}, ${it.agncyprov}" },
                    selectedItem = selectedAgencyName,
                    enabled = true,
                    onItemSelected = { name ->
                        selectedAgencyName = name
                        selectedAgencyId =
                            agencies.find { "${it.agncycity}, ${it.agncyprov}" == name }?.id ?: 0
                    }
                )


                PasswordTextField(
                    password = password,
                    onPasswordChange = { password = it },
                    isPasswordVisible = isPasswordVisible,
                    onToggleVisibility = { isPasswordVisible = !isPasswordVisible }
                )

                PasswordTextField(
                    password = confirmPassword,
                    onPasswordChange = { confirmPassword = it },
                    isPasswordVisible = isPasswordVisible,
                    onToggleVisibility = { isPasswordVisible = !isPasswordVisible },
                    label = "Confirm Password"
                )

                Spacer(Modifier.height(16.dp))

                SolidButton(
                    text = "Register",
                    enabled = listOf(
                        firstName, lastName, phoneNumber, email,
                        selectedPosition, selectedAgencyId.toString(),
                        password.trim(), confirmPassword.trim()
                    ).all { it.isNotBlank() } && password == confirmPassword,
                    onClick = {
                        if (password.trim() != confirmPassword.trim()) {
                            Toast.makeText(context, "Passwords do not match", Toast.LENGTH_SHORT)
                                .show()
                            return@SolidButton
                        }

                        viewModel.registerAgent(
                            firstName = firstName,
                            middleInitial = middleInitial.ifBlank { null },
                            lastName = lastName,
                            phone = phoneNumber,
                            email = email.lowercase().trim(),
                            password = password.trim(),
                            position = "agent",
                            agencyId = selectedAgencyId,
                            imageUri = profilePhotoUri,
                            contentResolver = context.contentResolver
                        )
                    },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(Modifier.height(12.dp))

                OutlineButton(
                    text = "Already have an account? Login",
                    onClick = { navController.navigate("login") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

fun createTempImageUri(context: Context): Uri {
    val imageFile = File(context.cacheDir, "camera_profile_${System.currentTimeMillis()}.jpg")
    imageFile.createNewFile()
    return FileProvider.getUriForFile(
        context,
        "${context.packageName}.provider",
        imageFile
    )
}

