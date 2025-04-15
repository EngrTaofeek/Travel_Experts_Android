package com.travelexperts.travelexpertsadmin.data.api.repositories

import com.travelexperts.travelexpertsadmin.data.ChatInteractionDTO
import com.travelexperts.travelexpertsadmin.data.ChatMessage
import com.travelexperts.travelexpertsadmin.data.ChatableUserDTO
import com.travelexperts.travelexpertsadmin.data.api.ApiService
import com.travelexperts.travelexpertsadmin.utils.NetworkResult
import javax.inject.Inject

class ChatRepository @Inject constructor(
    private val apiService: ApiService
) {
    suspend fun getInteractions(userId: String): NetworkResult<List<ChatInteractionDTO>> = try {
        val response = apiService.getInteractions(userId)
        if (response.isSuccessful) NetworkResult.Success(response.body() ?: emptyList())
        else NetworkResult.Failure("Error ${response.code()}: ${response.message()}")
    } catch (e: Exception) {
        NetworkResult.Failure(e.localizedMessage ?: "Unexpected error")
    }

    suspend fun getContacts(userId: Int, role: String): NetworkResult<List<ChatableUserDTO>> = try {
        val response = apiService.getContacts(userId, role)
        if (response.isSuccessful) NetworkResult.Success(response.body() ?: emptyList())
        else NetworkResult.Failure("Error ${response.code()}: ${response.message()}")
    } catch (e: Exception) {
        NetworkResult.Failure(e.localizedMessage ?: "Unexpected error")
    }

    suspend fun getChatHistory(user1: String, user2: String): NetworkResult<List<ChatMessage>> = try {
        val response = apiService.getChatHistory(user1, user2)
        if (response.isSuccessful) NetworkResult.Success(response.body() ?: emptyList())
        else NetworkResult.Failure("Error ${response.code()}: ${response.message()}")
    } catch (e: Exception) {
        NetworkResult.Failure(e.localizedMessage ?: "Unexpected error")
    }
}
