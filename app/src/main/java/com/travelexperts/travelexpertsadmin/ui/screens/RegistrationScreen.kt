package com.travelexperts.travelexpertsadmin.ui.screens


import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.*
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.travelexperts.travelexpertsadmin.R
import com.travelexperts.travelexpertsadmin.ui.components.DropdownMenuComponent
import com.travelexperts.travelexpertsadmin.ui.components.EmailTextField
import com.travelexperts.travelexpertsadmin.ui.components.OutlineButton
import com.travelexperts.travelexpertsadmin.ui.components.PasswordTextField
import com.travelexperts.travelexpertsadmin.ui.components.SolidButton

@Composable
fun RegisterScreen(navController: NavController) {
    val context = LocalContext.current

    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var selectedPosition by remember { mutableStateOf("") }
    var selectedAgency by remember { mutableStateOf("") }
    var profilePhotoUri by remember { mutableStateOf<Uri?>(null) }

    val positions = listOf("Manager", "Supervisor", "Staff", "Intern")
    val agencies = listOf("Agency A", "Agency B", "Agency C")

    var isPasswordVisible by remember { mutableStateOf(false) }

    // Image Pickers
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri -> profilePhotoUri = uri }
    )

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview(),
        onResult = { bitmap ->
            if (bitmap != null) {
                // Convert bitmap to URI logic here (if needed)
            }
        }
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center
    ) {
        Text("Register", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(16.dp))

        // Profile Image Selection
        Box(
            modifier = Modifier
                .size(100.dp)
                .clickable { galleryLauncher.launch("image/*") },
            contentAlignment = Alignment.Center
        ) {
            if (profilePhotoUri != null) {
                AsyncImage(
                    model = profilePhotoUri,
                    contentDescription = "Profile Picture",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            } else {
                Image(
                    painter = painterResource(id = R.drawable.baseline_person_24), // Replace with your placeholder
                    contentDescription = "Placeholder",
                    modifier = Modifier.fillMaxSize()
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Input Fields
        OutlinedTextField(value = firstName, onValueChange = { firstName = it }, label = { Text("First Name") }, modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(value = lastName, onValueChange = { lastName = it }, label = { Text("Last Name") }, modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = phoneNumber,
            onValueChange = { if (it.all { char -> char.isDigit() }) phoneNumber = it },
            label = { Text("Phone Number") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Phone),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        EmailTextField(email = email, onEmailChange = { email = it })
        Spacer(modifier = Modifier.height(8.dp))

        // Position Dropdown
        DropdownMenuComponent(label = "Position", items = positions, selectedItem = selectedPosition, onItemSelected = { selectedPosition = it })
        Spacer(modifier = Modifier.height(8.dp))

        // Agency Dropdown
        DropdownMenuComponent(label = "Agency", items = agencies, selectedItem = selectedAgency, onItemSelected = { selectedAgency = it })
        Spacer(modifier = Modifier.height(8.dp))

        PasswordTextField(
            password = password,
            onPasswordChange = { password = it },
            isPasswordVisible = isPasswordVisible,
            onToggleVisibility = { isPasswordVisible = !isPasswordVisible }
        )
        Spacer(modifier = Modifier.height(8.dp))

        PasswordTextField(
            password = confirmPassword,
            onPasswordChange = { confirmPassword = it },
            isPasswordVisible = isPasswordVisible,
            onToggleVisibility = { isPasswordVisible = !isPasswordVisible },
            label = "Confirm Password"
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Submit Button with Validation
        SolidButton(
            text = "Register",
            enabled = firstName.isNotEmpty() && lastName.isNotEmpty() && phoneNumber.isNotEmpty() &&
                    email.isNotEmpty() && selectedPosition.isNotEmpty() && selectedAgency.isNotEmpty() &&
                    password.isNotEmpty() && confirmPassword == password,
            onClick = {
                if (password != confirmPassword) {
                    Toast.makeText(context, "Passwords do not match", Toast.LENGTH_SHORT).show()
                } else {
                    // Handle Registration Logic
                }
            },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Already have an account? Go to Login
        OutlineButton(
            text = "Already have an account? Login",
            onClick = { navController.navigate("login") },
            modifier = Modifier.fillMaxWidth()
        )
    }
}
