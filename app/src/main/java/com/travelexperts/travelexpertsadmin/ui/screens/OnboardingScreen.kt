package com.travelexperts.travelexpertsadmin.ui.screens

import com.travelexperts.travelexpertsadmin.R
import com.travelexperts.travelexpertsadmin.ui.components.OutlineButton
import com.travelexperts.travelexpertsadmin.ui.components.SolidButton

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun OnboardingScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Top Image
        Image(
            painter = painterResource(id = R.drawable.onboarding), // Replace with your drawable
            contentDescription = "Onboarding Image",
            modifier = Modifier.size(200.dp)
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Welcome Text
        Text(
            text = "Welcome to TravelExperts",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Get the best experience with our app. Login or Register to get started.",
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal,
            textAlign = TextAlign.Center,
            color = Color.Gray,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(40.dp))

        // Login Button
        SolidButton(
            text = "Login",
            onClick = { navController.navigate("login") }, // Replace with your login route
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Register Button
        OutlineButton(
            text = "Register",
            onClick = { navController.navigate("register") }, // Replace with your register route
            modifier = Modifier.fillMaxWidth()
        )
    }
}
