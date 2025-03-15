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
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import com.travelexperts.travelexpertsadmin.R
import com.travelexperts.travelexpertsadmin.ui.components.CustomCardView
import com.travelexperts.travelexpertsadmin.ui.theme.ExploreBackgroundColor
import com.travelexperts.travelexpertsadmin.ui.theme.ExploreTitleColor
import com.travelexperts.travelexpertsadmin.ui.theme.MessageBlue
import com.travelexperts.travelexpertsadmin.ui.theme.ProfileBackgroundColor
import com.travelexperts.travelexpertsadmin.ui.theme.ProfileTitleColor
import com.travelexperts.travelexpertsadmin.ui.theme.ReviewBackgroundColor
import com.travelexperts.travelexpertsadmin.ui.theme.ReviewTitleColor
import com.travelexperts.travelexpertsadmin.ui.theme.ScanBackgroundColor
import com.travelexperts.travelexpertsadmin.ui.theme.ScanTitleColor
import com.travelexperts.travelexpertsadmin.ui.theme.TropicalBlue
import com.travelexperts.travelexpertsadmin.ui.theme.WalletBackgroundColor
import com.travelexperts.travelexpertsadmin.ui.theme.WalletTitleColor


data class GridItem(val title: String, val description: String,val titleColor: Color, val backgroundColor: Color, val image: Painter)

@Composable
fun HomeScreen(navController: NavController) {


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center
    ) {

        Spacer(modifier = Modifier.height(16.dp))
        GridScreen(navController)
        Spacer(modifier = Modifier.height(12.dp))


    }
}






@Composable
fun GridScreen(navController: NavController) {
    val items = listOf(
        GridItem("Messages", "Send and receive messages from customer .", MessageBlue,
            TropicalBlue, painterResource(id = R.drawable.messages)),
        GridItem("Explore Attractions", "Explore tourist locations in different cities",
            ExploreTitleColor, ExploreBackgroundColor, painterResource(id = R.drawable.attractions)),
        GridItem("Review Agents", "Review Pending agents and approve .",
            ReviewTitleColor, ReviewBackgroundColor, painterResource(id = R.drawable.review)),
        GridItem("Profile", "View and edit your profile with recent updates.", ProfileTitleColor,
            ProfileBackgroundColor, painterResource(id = R.drawable.dashboard_profile)),
        GridItem("Scan Booking", "This feature is coming soon and not yet available.", ScanTitleColor, ScanBackgroundColor, painterResource(id = R.drawable.dashboard_scan)),
        GridItem("Suppliers", "View and manage all your suppliers.", WalletTitleColor,
            WalletBackgroundColor, painterResource(id = R.drawable.wallet))
    )

    LazyVerticalGrid(
        columns = GridCells.Fixed(2), // 2 Columns
        state = rememberLazyGridState(),
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(items.size) { index ->
            CustomCardView(
                title = items[index].title,
                description = items[index].description,
                titleColor = items[index].titleColor,
                cardBackGroundColor = items[index].backgroundColor,
                image = items[index].image
            ){
//                navController.navigate("replace_this_with_the_route_name")
                navController.navigate("suppliers")
            }
        }
    }
}

