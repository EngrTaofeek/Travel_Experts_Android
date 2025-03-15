package com.travelexperts.travelexpertsadmin.data


import androidx.compose.ui.graphics.Color

data class ProfileData(
    val profileImage: Int, // Drawable Resource ID
    val firstName: String,
    val lastName: String,
    val phoneNumber: String,
    val email: String,
    val position: String,
    val agency: String,
    val status: String, // Active, Inactive, Suspended
)

// Map Status to Colors
fun getStatusColor(status: String): Color {
    return when (status) {
        "Active" -> Color.Green
        "Pending" -> Color.Yellow
        "Suspended" -> Color.Red
        else -> Color.Blue
    }
}
