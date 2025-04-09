package com.travelexperts.travelexpertsadmin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.travelexperts.travelexpertsadmin.ui.components.BackButtonAppBar
import com.travelexperts.travelexpertsadmin.ui.components.ProfileAppBar
import com.travelexperts.travelexpertsadmin.ui.navigation.BottomNavigationBar
import com.travelexperts.travelexpertsadmin.ui.navigation.NavGraph
import com.travelexperts.travelexpertsadmin.ui.navigation.Routes
import com.travelexperts.travelexpertsadmin.ui.theme.TravelExpertsAdminTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val splashscreen = installSplashScreen()
        var keepSplashScreen = true

        splashscreen.setKeepOnScreenCondition { keepSplashScreen }
        lifecycleScope.launch {
            delay(5000)
            keepSplashScreen = false
        }
        enableEdgeToEdge()


        setContent {
            val navController = rememberNavController()
            val navBackStackEntry = navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry.value?.destination?.route
            val showBottomBar = currentRoute in listOf(
                Routes.HOME, Routes.PACKAGES, Routes.PRODUCTS, Routes.MANAGE_CUSTOMERS
            )

            val showProfileAppBar = currentRoute in listOf(
                Routes.HOME
            )

            val showBackButtonAppBar = currentRoute !in listOf(
                Routes.ONBOARDING, Routes.LOGIN, Routes.REGISTER
            ) && !showProfileAppBar

            val routeTitle = when {
                currentRoute?.startsWith(Routes.EDIT_CUSTOMER) == true -> "Edit Customer"
                currentRoute?.startsWith(Routes.PACKAGE_DETAIL) == true -> "Package Detail"
                currentRoute?.startsWith(Routes.EDIT_BOOKING) == true -> "Edit Booking"
                currentRoute?.startsWith(Routes.MANAGE_CUSTOMERS) == true -> "Customers"
                currentRoute?.startsWith(Routes.PACKAGES) == true -> "Packages"
                currentRoute?.startsWith(Routes.PRODUCTS) == true -> "Product"
                currentRoute?.startsWith(Routes.CUSTOMER_BOOKINGS) == true -> "Customer Bookings"
                currentRoute == Routes.MESSAGES -> "Messages"
                currentRoute?.startsWith(Routes.CHAT) == true -> "Chat"
                currentRoute == Routes.REVIEW_AGENTS -> "Review Agents"
                currentRoute == Routes.TOURIST_ACTIONS -> "Tourist Attractions"
                else -> "Header"
            }


            TravelExpertsAdminTheme {
                Scaffold(
                    topBar = {
                        when {
                            showProfileAppBar -> ProfileAppBar(
                                profileImage = painterResource(id = R.drawable.woman),
                                userName = "John Doe",
                                subtitle = "Admin",
                                backgroundImage = painterResource(id = R.drawable.dashboard_top_background),
                                onNotificationClick = { /* Handle Notifications */ }
                            )

                            showBackButtonAppBar -> BackButtonAppBar(
                                title = routeTitle,
                                backgroundImage = painterResource(id = R.drawable.app_bar_background),
                                navController = navController
                            )
                        }
                    },
                    bottomBar = {
                        if (showBottomBar) {
                            BottomNavigationBar(navController)
                        }
                    }
                ) { paddingValues ->
                    NavGraph(navController = navController, modifier = Modifier.padding(paddingValues))
                }
            }
        }
    }


}
