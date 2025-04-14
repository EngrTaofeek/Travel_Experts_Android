package com.travelexperts.travelexpertsadmin.utils

import android.util.Log
import com.travelexperts.travelexpertsadmin.di.BASE_URL

fun resolveImageUrl(relativePath: String?): String {
     // Replace with your actual base server URL
    return if (!relativePath.isNullOrBlank()) {
        Log.d("teekay from profile", "${BASE_URL.dropLast(1)}$relativePath")
        "${BASE_URL.dropLast(1)}$relativePath"
    } else {
        "" // fallback URL or empty string
    }
}
