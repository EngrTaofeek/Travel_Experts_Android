package com.travelexperts.travelexpertsadmin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.travelexperts.travelexpertsadmin.datastore.UserPreferences
import com.travelexperts.travelexpertsadmin.ui.components.BackButtonAppBar
import com.travelexperts.travelexpertsadmin.ui.components.ProfileAppBar
import com.travelexperts.travelexpertsadmin.ui.navigation.BottomNavigationBar
import com.travelexperts.travelexpertsadmin.ui.navigation.NavGraph
import com.travelexperts.travelexpertsadmin.ui.navigation.Routes
import com.travelexperts.travelexpertsadmin.ui.theme.TravelExpertsAdminTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import androidx.compose.runtime.*
import coil.compose.rememberAsyncImagePainter
import com.travelexperts.travelexpertsadmin.di.BASE_URL

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val splashscreen = installSplashScreen()
        var keepSplashScreen = true

        var startDestination by mutableStateOf<String?>(null)
        lifecycleScope.launch {

            UserPreferences.isLoggedIn(applicationContext).collect { loggedIn ->
                startDestination = if (loggedIn) Routes.HOME else Routes.ONBOARDING
                keepSplashScreen = false
            }
        }

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
                currentRoute == Routes.PROFILE -> "Profile"
                currentRoute == Routes.TOURIST_ACTIONS -> "Tourist Attractions"
                else -> "Header"
            }

            val context = LocalContext.current
            val fullName by UserPreferences.getFullName(context).collectAsState(initial = "")
            val role by UserPreferences.getPosition(context).collectAsState(initial = "")
            val profilePhotoUrl by UserPreferences.getProfilePhoto(context).collectAsState(initial = "")
            val imagePainter = rememberAsyncImagePainter(
                model = if (!profilePhotoUrl.isNullOrBlank()) BASE_URL.dropLast(1) + profilePhotoUrl else R.drawable.baseline_person_24,
                error = painterResource(R.drawable.baseline_person_24),
                placeholder = painterResource(R.drawable.baseline_person_24)
            )

            TravelExpertsAdminTheme {
                Scaffold(
                    topBar = {
                        when {
                            showProfileAppBar -> ProfileAppBar(
                                profileImage = imagePainter,
                                userName = fullName ?: "",
                                subtitle = role ?: "",
                                backgroundImage = painterResource(id = R.drawable.dashboard_top_background),
                                onProfileClick = {navController.navigate(Routes.PROFILE) },
                                onLogoutClick = {
                                    lifecycleScope.launch {
                                        UserPreferences.clearAll(context)
                                    }
                                    navController.navigate(Routes.ONBOARDING) {
                                        popUpTo(0) { inclusive = true } // clear backstack
                                    }
                                }
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
                    NavGraph(navController = navController, startDestination = startDestination ?: Routes.ONBOARDING, modifier = Modifier.padding(paddingValues))
                }
            }
        }
    }


}
