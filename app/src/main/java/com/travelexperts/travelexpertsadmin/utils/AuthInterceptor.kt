package com.travelexperts.travelexpertsadmin.utils

import android.content.Context
import com.travelexperts.travelexpertsadmin.datastore.UserPreferences
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val context: Context
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()

        // Skip Authorization for login endpoint
        if (original.url.encodedPath.contains("/api/user/login")) {
            return chain.proceed(original)
        }

        val token = runBlocking {
            UserPreferences.getBearerToken(context).firstOrNull() ?: ""
        }

        val newRequest = original.newBuilder()
            .addHeader("Authorization", "Bearer $token")
            .build()

        return chain.proceed(newRequest)
    }
}

