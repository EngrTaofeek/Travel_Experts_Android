package com.travelexperts.travelexpertsadmin.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.travelexperts.travelexpertsadmin.ui.screens.ChatScreen
import com.travelexperts.travelexpertsadmin.ui.screens.CustomerBookingsScreen
import com.travelexperts.travelexpertsadmin.ui.screens.CustomersScreen
import com.travelexperts.travelexpertsadmin.ui.screens.EditBookingScreen
import com.travelexperts.travelexpertsadmin.ui.screens.EditCustomerScreen
import com.travelexperts.travelexpertsadmin.ui.screens.HomeScreen
import com.travelexperts.travelexpertsadmin.ui.screens.LoginScreen
import com.travelexperts.travelexpertsadmin.ui.screens.ManageCustomersScreen
import com.travelexperts.travelexpertsadmin.ui.screens.MessagesListScreen
import com.travelexperts.travelexpertsadmin.ui.screens.OnboardingScreen
import com.travelexperts.travelexpertsadmin.ui.screens.PackageDetailScreen
import com.travelexperts.travelexpertsadmin.ui.screens.PackagesScreen
import com.travelexperts.travelexpertsadmin.ui.screens.PendingAgentsScreen
import com.travelexperts.travelexpertsadmin.ui.screens.ProductsScreen
import com.travelexperts.travelexpertsadmin.ui.screens.ProfileScreen
import com.travelexperts.travelexpertsadmin.ui.screens.RegisterScreen
import com.travelexperts.travelexpertsadmin.ui.screens.SuppliersScreen
import com.travelexperts.travelexpertsadmin.ui.screens.TouristAttractionsScreen

@Composable
fun NavGraph(navController: NavHostController, modifier: Modifier) {
    NavHost(
        navController = navController,
        startDestination = Routes.HOME,
        modifier = modifier
    ) {
        composable(Routes.ONBOARDING) { OnboardingScreen(navController) }
        composable(Routes.LOGIN) { LoginScreen(navController) }
        composable(Routes.REGISTER) { RegisterScreen(navController) }

        composable(Routes.HOME) { HomeScreen(navController) }
        composable(Routes.PACKAGES) { PackagesScreen(navController) }
        composable(Routes.PRODUCTS) { ProductsScreen(navController) }
        composable(Routes.SUPPLIERS) { SuppliersScreen(navController) }
        composable(Routes.CUSTOMERS) { CustomersScreen(navController) }
        composable(Routes.PROFILE) { ProfileScreen(navController) }

        composable("${Routes.PACKAGE_DETAIL}/{packageId}") { backStackEntry ->
            val packageId = backStackEntry.arguments?.getString("packageId")?.toIntOrNull() ?: 0
            PackageDetailScreen(navController, packageId)
        }

        composable(Routes.MANAGE_CUSTOMERS) { ManageCustomersScreen(navController) }
        composable("${Routes.EDIT_CUSTOMER}/{customerId}") { backStackEntry ->
            val customerId = backStackEntry.arguments?.getString("customerId")?.toInt() ?: 0
            EditCustomerScreen(navController, customerId)
        }

        composable("${Routes.CUSTOMER_BOOKINGS}/{customerId}") { backStackEntry ->
            val customerId = backStackEntry.arguments?.getString("customerId")?.toInt() ?: 0
            CustomerBookingsScreen(navController, customerId)
        }

        composable("${Routes.EDIT_BOOKING}/{bookingId}") { backStackEntry ->
            val bookingId = backStackEntry.arguments?.getString("bookingId")?.toInt() ?: 0
            EditBookingScreen(navController, bookingId)
        }

        composable(Routes.MESSAGES) { MessagesListScreen(navController) }
        composable(Routes.REVIEW_AGENTS) { PendingAgentsScreen(navController) }
        composable(Routes.TOURIST_ACTIONS) { TouristAttractionsScreen(navController) }

        composable("${Routes.CHAT}/{customerId}") { backStackEntry ->
            val customerId = backStackEntry.arguments?.getString("customerId")?.toInt() ?: 0
            ChatScreen(customerId, navController)
        }
    }
}
