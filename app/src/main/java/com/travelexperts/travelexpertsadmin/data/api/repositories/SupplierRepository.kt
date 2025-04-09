package com.travelexperts.travelexpertsadmin.data.api.repositories

import com.travelexperts.travelexpertsadmin.data.api.ApiService
import com.travelexperts.travelexpertsadmin.data.api.response.Supplier
import com.travelexperts.travelexpertsadmin.utils.NetworkResult
import javax.inject.Inject


class SupplierRepository @Inject constructor(
        private val apiService: ApiService
    ) {
        suspend fun getSuppliers(): NetworkResult<List<Supplier>> {
            return try {
                val response = apiService.getSuppliers()
                if (response.isSuccessful && response.body() != null) {
                    NetworkResult.Success(response.body()!!)
                } else {
                    NetworkResult.Failure("Error ${response.code()}: ${response.message()}")
                }
            } catch (e: Exception) {
                NetworkResult.Failure(e.localizedMessage ?: "Unexpected error occurred")
            }
        }

        suspend fun updateSupplier(supplier: Supplier): NetworkResult<Supplier> {
            return try {
                val response = apiService.updateSupplier(supplier.id, supplier)
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

