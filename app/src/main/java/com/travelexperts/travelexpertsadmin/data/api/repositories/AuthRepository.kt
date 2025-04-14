package com.travelexperts.travelexpertsadmin.data.api.repositories

import android.content.Context
import android.util.Log
import com.travelexperts.travelexpertsadmin.data.api.ApiService
import com.travelexperts.travelexpertsadmin.datastore.UserPreferences
import com.travelexperts.travelexpertsadmin.utils.NetworkResult
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val apiService: ApiService
) {
    suspend fun login(email: String, password: String): NetworkResult<String> = try {
        val response = apiService.loginUser(email, password)
        if (response.isSuccessful && response.body() != null) {
            NetworkResult.Success(response.body()!!.token)
        } else {
            NetworkResult.Failure("Login failed: ${response.code()} ${response.message()}")
        }
    } catch (e: Exception) {
        NetworkResult.Failure("Login failed: ${e.localizedMessage ?: "Unexpected error"}")
    }
    suspend fun fetchAgentProfileByEmail(email: String, context: Context): NetworkResult<Unit> = try {
        val response = apiService.getAgentByEmail(email)
        if (response.isSuccessful) {
            val agent = response.body()!!

            // Save agent details to DataStore
            UserPreferences.setUserId(context, agent.id.toString())
            UserPreferences.setEmail(context, agent.agtemail)
            UserPreferences.setFullName(context, "${agent.agtfirstname} ${agent.agtlastname}")
            UserPreferences.setRole(context, agent.role ?: "")
            UserPreferences.setPosition(context, agent.agtposition ?: "")
            UserPreferences.setStatus(context, agent.status ?: "")
            UserPreferences.setProfilePhoto(context, agent.profileImageUrl ?: "")

            NetworkResult.Success(Unit)
        } else {
            Log.i("teekay", "Agent fetch failed: ${response.code()} - ${response.message()}")

            NetworkResult.Failure("Error: ${response.code()} ${response.message()}")
        }
    } catch (e: Exception) {
        NetworkResult.Failure(e.message ?: "Unexpected error")
    }

}
