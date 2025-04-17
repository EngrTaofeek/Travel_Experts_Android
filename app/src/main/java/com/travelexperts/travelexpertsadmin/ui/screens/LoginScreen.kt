package com.travelexperts.travelexpertsadmin.ui.screens



import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.travelexperts.travelexpertsadmin.R
import com.travelexperts.travelexpertsadmin.ui.components.CustomLoader
import com.travelexperts.travelexpertsadmin.ui.components.EmailTextField
import com.travelexperts.travelexpertsadmin.ui.components.OutlineButton
import com.travelexperts.travelexpertsadmin.ui.components.PasswordTextField
import com.travelexperts.travelexpertsadmin.ui.components.RegisterTextButton
import com.travelexperts.travelexpertsadmin.ui.components.SolidButton
import com.travelexperts.travelexpertsadmin.ui.navigation.Routes
import com.travelexperts.travelexpertsadmin.utils.NetworkResult
import com.travelexperts.travelexpertsadmin.viewmodels.AuthenticationViewModel

@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: AuthenticationViewModel = hiltViewModel()
) {
    val loginState by viewModel.loginState.collectAsState()
    val context = LocalContext.current

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isPasswordVisible by remember { mutableStateOf(false) }
    val snackbarHostState = remember { SnackbarHostState() }

    // 🎯 React to login state
    LaunchedEffect(loginState) {
        when (loginState) {
            is NetworkResult.Loading -> {}
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

    Scaffold(snackbarHost = { SnackbarHost(snackbarHostState) }) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Top Image
            Image(
                painter = painterResource(id = R.drawable.login_page_vector), // Replace with your drawable
                contentDescription = "Onboarding Image",
                modifier = Modifier.size(200.dp)
            )

            Spacer(modifier = Modifier.height(32.dp))
            Text(
                text = "Welcome back",
                style = MaterialTheme.typography.headlineLarge.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 32.sp // Adjust font size as needed
                )
            )
            Text(
                text = "Sign in",
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp // Adjust font size as needed
                )
            )
            Spacer(Modifier.height(16.dp))

            EmailTextField(email, onEmailChange = { email = it })
            Spacer(Modifier.height(12.dp))

            PasswordTextField(
                password = password,
                onPasswordChange = { password = it },
                isPasswordVisible = isPasswordVisible,
                onToggleVisibility = { isPasswordVisible = !isPasswordVisible }
            )
            if (loginState is NetworkResult.Loading) {
                CircularProgressIndicator()
            }
            Spacer(Modifier.height(24.dp))

            SolidButton(
                text = "Login",
                onClick = {
                    if (email.isNotEmpty() && password.isNotEmpty()) {
                        viewModel.login(email.lowercase().trim(), password)
                    } else {
                        //replace with snackbar or dialog
                        Toast.makeText(context,"Please enter email and password",Toast.LENGTH_SHORT).show()
                    }
                          },
                modifier = Modifier.fillMaxWidth(),
            )
            Spacer(Modifier.height(12.dp))
            RegisterTextButton { navController.navigate(Routes.REGISTER) }

        }
    }
}
