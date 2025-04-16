package com.travelexperts.travelexpertsadmin

import android.app.Application
import com.google.android.libraries.places.api.Places
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        if (!Places.isInitialized()) {
            Places.initialize(applicationContext, "AIzaSyD_XHq1jOa5NcZQ3lIaL98zaVFVB1X1vyo")
        }
    }
}
