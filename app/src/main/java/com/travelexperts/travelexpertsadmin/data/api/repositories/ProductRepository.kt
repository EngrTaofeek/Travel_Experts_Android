package com.travelexperts.travelexpertsadmin.data.api.repositories

import com.travelexperts.travelexpertsadmin.data.api.ApiService
import com.travelexperts.travelexpertsadmin.data.api.response.Product
import com.travelexperts.travelexpertsadmin.utils.NetworkResult
import javax.inject.Inject

// repository/ProductRepository.kt
class ProductRepository @Inject constructor(
    private val apiService: ApiService
) {
    suspend fun getProducts(): NetworkResult<List<Product>> {
        return try {
            val response = apiService.getProducts()
            if (response.isSuccessful && response.body() != null) {
                NetworkResult.Success(response.body()!!)
            } else {
                NetworkResult.Failure("Error ${response.code()}: ${response.message()}")
            }
        } catch (e: Exception) {
            NetworkResult.Failure(e.localizedMessage ?: "Unexpected error occurred")
        }
    }

    suspend fun updateProduct(product: Product): NetworkResult<Product> {
        return try {
            val response = apiService.updateProduct(product.id, product)
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


