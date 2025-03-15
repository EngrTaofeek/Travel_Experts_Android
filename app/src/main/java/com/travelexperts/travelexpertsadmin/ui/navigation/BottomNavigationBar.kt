package com.travelexperts.travelexpertsadmin.ui.navigation


import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.travelexperts.travelexpertsadmin.R
import com.travelexperts.travelexpertsadmin.ui.theme.Primary

data class BottomNavItem(
    val title: String,
    val icon: Int,
    val route: String
)

val bottomNavItems = listOf(
    BottomNavItem("Home", R.drawable.ic_home, "home"),
    BottomNavItem("Packages", R.drawable.ic_packages, "packages"),
    BottomNavItem("Products", R.drawable.ic_products, "products"),
    BottomNavItem("Customers", R.drawable.ic_customers, "customers")
)

@Composable
fun BottomNavigationBar(navController: NavController) {
    val navBackStackEntry = navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry.value?.destination

    NavigationBar (containerColor = Color.White){
        bottomNavItems.forEach { item ->
            val isSelected = currentDestination?.route == item.route
            val tint = if (isSelected) Primary else Color.Gray

            NavigationBarItem(
                selected = isSelected,
                onClick = { navController.navigate(item.route) },
                icon = {
                    val painter: Painter = painterResource(id = item.icon)
                    Icon(painter = painter, contentDescription = item.title, tint = tint)
                },
                label = { Text(item.title, color = tint) },
                alwaysShowLabel = true
            )
        }
    }
}
