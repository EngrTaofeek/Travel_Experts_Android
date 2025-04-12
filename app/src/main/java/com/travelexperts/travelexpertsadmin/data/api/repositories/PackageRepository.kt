package com.travelexperts.travelexpertsadmin.data.api.repositories

import com.travelexperts.travelexpertsadmin.data.api.ApiService
import com.travelexperts.travelexpertsadmin.data.api.response.PackageData
import com.travelexperts.travelexpertsadmin.utils.NetworkResult
import okhttp3.MultipartBody
import okhttp3.Response
import javax.inject.Inject

class PackageRepository @Inject constructor(
    private val apiService: ApiService
) {
    suspend fun getAllPackages(): NetworkResult<List<PackageData>> = try {
        val response = apiService.getAllPackages()
        if (response.isSuccessful) NetworkResult.Success(response.body() ?: emptyList())
        else NetworkResult.Failure("Error ${response.code()}: ${response.message()}")
    } catch (e: Exception) {
        NetworkResult.Failure(e.message ?: "Unexpected error")
    }

    suspend fun getPackageById(id: Int): NetworkResult<PackageData> = try {
        val response = apiService.getPackageById(id)
        if (response.isSuccessful) NetworkResult.Success(response.body()!!)
        else NetworkResult.Failure("Error ${response.code()}: ${response.message()}")
    } catch (e: Exception) {
        NetworkResult.Failure(e.message ?: "Unexpected error")
    }

    suspend fun updatePackage(pkg: PackageData): NetworkResult<PackageData> = try {
        val response = apiService.updatePackage(pkg.id, pkg)
        if (response.isSuccessful) NetworkResult.Success(response.body()!!)
        else NetworkResult.Failure("Error ${response.code()}: ${response.message()}")
    } catch (e: Exception) {
        NetworkResult.Failure(e.message ?: "Unexpected error")
    }

    suspend fun uploadPackageImage(id: Int, image: MultipartBody.Part): NetworkResult<Unit> = try {
        val response = apiService.uploadPackageImage(id, image)
        if (response.isSuccessful) NetworkResult.Success(Unit)
        else NetworkResult.Failure("Error ${response.code()}: ${response.message()}")
    } catch (e: Exception) {
        NetworkResult.Failure(e.message ?: "Unexpected error")
    }
}
