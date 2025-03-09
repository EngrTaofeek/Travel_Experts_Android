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

@Composable
fun LoginScreen(navController: NavController) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isPasswordVisible by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Login",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Email Input Field
        EmailTextField(
            email = email,
            onEmailChange = { email = it }
        )


        Spacer(modifier = Modifier.height(12.dp))

        // Password Input Field
        PasswordTextField(
            password = password,
            onPasswordChange = { password = it },
            isPasswordVisible = isPasswordVisible,
            onToggleVisibility = { isPasswordVisible = !isPasswordVisible }
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Login Button
        SolidButton(
            text = "Login",
            onClick = { /* Handle Login Logic */ },
            modifier = Modifier.fillMaxWidth(),
            enabled = email.isNotEmpty() && password.isNotEmpty()
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Register Button
        OutlineButton(
            text = "Register",
            onClick = { navController.navigate("register") },
            modifier = Modifier.fillMaxWidth()
        )
    }
}
