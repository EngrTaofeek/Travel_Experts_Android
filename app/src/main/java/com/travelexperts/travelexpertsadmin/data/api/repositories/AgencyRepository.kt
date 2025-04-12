package com.travelexperts.travelexpertsadmin.data.api.repositories

import com.travelexperts.travelexpertsadmin.data.api.ApiService
import com.travelexperts.travelexpertsadmin.data.api.response.Agency
import com.travelexperts.travelexpertsadmin.utils.NetworkResult
import javax.inject.Inject

class AgencyRepository @Inject constructor(
    private val apiService: ApiService
) {
    suspend fun fetchAgencies(): NetworkResult<List<Agency>> = try {
        val res = apiService.getAgencies()
        if (res.isSuccessful) NetworkResult.Success(res.body() ?: emptyList())
        else NetworkResult.Failure("Error ${res.code()}: ${res.message()}")
    } catch (e: Exception) {
        NetworkResult.Failure(e.message ?: "Unexpected error")
    }

    suspend fun getAgencyById(id: Int): NetworkResult<Agency> = try {
        val response = apiService.getAgencyById(id)
        if (response.isSuccessful && response.body() != null) {
            NetworkResult.Success(response.body()!!)
        } else {
            NetworkResult.Failure("Error ${response.code()}: ${response.message()}")
        }
    } catch (e: Exception) {
        NetworkResult.Failure(e.message ?: "Unexpected error")
    }

}
