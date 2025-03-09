package com.travelexperts.travelexpertsadmin.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.travelexperts.travelexpertsadmin.ui.screens.LoginScreen
import com.travelexperts.travelexpertsadmin.ui.screens.OnboardingScreen
import com.travelexperts.travelexpertsadmin.ui.screens.RegisterScreen

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = "onboarding"
    ) {

        //below is sample of passing arguments to a composable during navigation
//        composable("profile/{userId}") { backStackEntry ->
//            val userId = backStackEntry.arguments?.getString("userId") ?: ""
//            ProfileScreen(navController, userId)
//        }
        composable("onboarding") { OnboardingScreen(navController) }
        composable("login") { LoginScreen(navController) } // Replace with actual LoginScreen
        composable("register") { RegisterScreen(navController) } // Replace with actual RegisterScreen

    }
}
