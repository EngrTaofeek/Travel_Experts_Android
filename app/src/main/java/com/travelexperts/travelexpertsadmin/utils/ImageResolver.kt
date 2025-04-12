package com.travelexperts.travelexpertsadmin.utils

import com.travelexperts.travelexpertsadmin.di.BASE_URL

fun resolveImageUrl(relativePath: String?): String {
     // Replace with your actual base server URL
    return if (!relativePath.isNullOrBlank()) {
        "${BASE_URL.dropLast(1)}$relativePath"
    } else {
        "" // fallback URL or empty string
    }
}
