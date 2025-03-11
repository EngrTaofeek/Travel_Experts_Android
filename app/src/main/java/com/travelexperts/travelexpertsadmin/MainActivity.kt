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
import com.travelexperts.travelexpertsadmin.ui.theme.TravelExpertsAdminTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

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
            val showBottomBar = currentRoute in listOf("home", "packages", "products", "customers")
            val showProfileAppBar = currentRoute in listOf("home", "packages", "products", "customers")
            val showBackButtonAppBar = currentRoute in listOf("onboarding", "login", "register") && !showProfileAppBar


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
                                title = "Header",
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
@Composable
fun MyApp() {
    val navController = rememberNavController()
//    NavGraph(navController, androidx.compose.ui.Modifier.Companion.padding(paddingValues))
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TravelExpertsAdminTheme {
        Greeting("Teekay android")
    }
}