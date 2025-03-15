package com.travelexperts.travelexpertsadmin.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.travelexperts.travelexpertsadmin.ui.screens.CustomersScreen
import com.travelexperts.travelexpertsadmin.ui.screens.HomeScreen
import com.travelexperts.travelexpertsadmin.ui.screens.LoginScreen
import com.travelexperts.travelexpertsadmin.ui.screens.OnboardingScreen
import com.travelexperts.travelexpertsadmin.ui.screens.PackageDetailScreen
import com.travelexperts.travelexpertsadmin.ui.screens.PackagesScreen
import com.travelexperts.travelexpertsadmin.ui.screens.ProductsScreen
import com.travelexperts.travelexpertsadmin.ui.screens.ProfileScreen
import com.travelexperts.travelexpertsadmin.ui.screens.RegisterScreen
import com.travelexperts.travelexpertsadmin.ui.screens.SuppliersScreen

@Composable
fun NavGraph(navController: NavHostController, modifier: Modifier) {
    NavHost(
        navController = navController,
        startDestination = "home",
        modifier = modifier

    ) {

        //below is sample of passing arguments to a composable during navigation
//        composable("profile/{userId}") { backStackEntry ->
//            val userId = backStackEntry.arguments?.getString("userId") ?: ""
//            ProfileScreen(navController, userId)
//        }
        composable("onboarding") { OnboardingScreen(navController) }
        composable("login") { LoginScreen(navController) } // Replace with actual LoginScreen
        composable("register") { RegisterScreen(navController) } // Replace with actual RegisterScreen

        // Main App Screens (After Login)
        composable("home") { HomeScreen(navController) }
        composable("packages") { PackagesScreen(navController) }
        composable("products") { ProductsScreen(navController) }
        composable("suppliers") { SuppliersScreen(navController) }
        composable("customers") { CustomersScreen(navController) }
        composable("profile") { ProfileScreen(navController) }
        composable("packageDetail/{packageId}") { backStackEntry ->
            val packageId = backStackEntry.arguments?.getString("packageId")?.toIntOrNull() ?: 0
            PackageDetailScreen(navController, packageId)
        }


    }
}
