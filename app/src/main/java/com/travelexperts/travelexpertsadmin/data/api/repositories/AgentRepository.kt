package com.travelexperts.travelexpertsadmin.data.api.repositories

import com.travelexperts.travelexpertsadmin.data.api.ApiService
import com.travelexperts.travelexpertsadmin.data.api.response.Agent
import com.travelexperts.travelexpertsadmin.utils.NetworkResult
import javax.inject.Inject

class AgentRepository @Inject constructor(
    private val apiService: ApiService
) {
    suspend fun getAgents(): NetworkResult<List<Agent>> {
        return try {
            val response = apiService.getAgents()
            if (response.isSuccessful && response.body() != null) {
                NetworkResult.Success(response.body()!!)
            } else {
                NetworkResult.Failure("Error ${response.code()}: ${response.message()}")
            }
        } catch (e: Exception) {
            NetworkResult.Failure(e.localizedMessage ?: "Unexpected error occurred")
        }
    }
}