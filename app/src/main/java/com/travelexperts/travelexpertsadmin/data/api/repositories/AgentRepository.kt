package com.travelexperts.travelexpertsadmin.data.api.repositories

import android.content.ContentResolver
import android.net.Uri
import com.google.gson.Gson
import com.travelexperts.travelexpertsadmin.data.api.ApiService
import com.travelexperts.travelexpertsadmin.data.api.requests.RegisterAgentRequest
import com.travelexperts.travelexpertsadmin.data.api.response.Agent
import com.travelexperts.travelexpertsadmin.utils.NetworkResult
import com.travelexperts.travelexpertsadmin.utils.toJsonRequestBody
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject

class AgentRepository @Inject constructor(
    private val apiService: ApiService
) {
    suspend fun getAgents(): NetworkResult<List<Agent>> {
        return try {
            val response = apiService.getAgents()
            if (response.isSuccessful && response.body() != null) {
                val pendingAgents = response.body()!!.filter { it.status.equals("pending", ignoreCase = true) }
                NetworkResult.Success(pendingAgents)
            } else {
                NetworkResult.Failure("Error ${response.code()}: ${response.message()}")
            }
        } catch (e: Exception) {
            NetworkResult.Failure(e.localizedMessage ?: "Unexpected error occurred")
        }
    }

    suspend fun getAgent(id: Int): NetworkResult<Agent> = try {
        val res = apiService.getAgent(id)
        if (res.isSuccessful) NetworkResult.Success(res.body()!!)
        else NetworkResult.Failure("Error ${res.code()}")
    } catch (e: Exception) {
        NetworkResult.Failure(e.message ?: "Unknown error")
    }

    suspend fun updateAgent(id: Int, agent: Agent, imageUri: Uri?, contentResolver: ContentResolver): NetworkResult<Agent> = try {
        val agentBody = agent.toJsonRequestBody()
        val imagePart = imageUri?.let {
            val inputStream = contentResolver.openInputStream(it)
            val bytes = inputStream?.readBytes() ?: return NetworkResult.Failure("Image error")
            val requestBody = bytes.toRequestBody("image/*".toMediaTypeOrNull())
            MultipartBody.Part.createFormData("image", "profile.jpg", requestBody)
        }
        val response = apiService.updateAgent(id, agentBody, imagePart)
        if (response.isSuccessful) NetworkResult.Success(response.body()!!)
        else NetworkResult.Failure("Update failed: ${response.code()}")
    } catch (e: Exception) {
        NetworkResult.Failure(e.message ?: "Error")
    }



    suspend fun registerAgent(request: RegisterAgentRequest, imageUri: Uri?, contentResolver: ContentResolver): NetworkResult<Any> {
        return try {
            val json = Gson().toJson(request)
            val agentPart = RequestBody.create("application/json".toMediaTypeOrNull(), json)

            val imagePart = imageUri?.let {
                val inputStream = contentResolver.openInputStream(it)
                val bytes = inputStream?.readBytes() ?: return NetworkResult.Failure("Image error")
                val requestBody = bytes.toRequestBody("image/*".toMediaTypeOrNull())
                MultipartBody.Part.createFormData("image", "profile.jpg", requestBody)
            }

            val response = apiService.registerAgent(agentPart, imagePart)
            if (response.isSuccessful) {
                NetworkResult.Success(response.body() ?: "Registration successful")
            } else {
                NetworkResult.Failure("Error ${response.code()}: ${response.message()}")
            }
        } catch (e: Exception) {
            NetworkResult.Failure(e.message ?: "Unexpected error")
        }
    }
}