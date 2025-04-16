package com.travelexperts.travelexpertsadmin.data.api.repositories

import com.travelexperts.travelexpertsadmin.data.api.ApiService
import com.travelexperts.travelexpertsadmin.data.api.response.Booking
import com.travelexperts.travelexpertsadmin.data.api.response.Customer
import com.travelexperts.travelexpertsadmin.utils.NetworkResult
import javax.inject.Inject

class CustomerRepository @Inject constructor(
    private val apiService: ApiService
) {
    suspend fun getCustomers(agentId: Int): NetworkResult<List<Customer>> = try {
        val response = apiService.getAllCustomers(agentId)
        if (response.isSuccessful) NetworkResult.Success(response.body() ?: emptyList())
        else NetworkResult.Failure("Error ${response.code()}: ${response.message()}")
    } catch (e: Exception) {
        NetworkResult.Failure(e.message ?: "Unexpected error")
    }

    suspend fun getCustomer(id: Int): NetworkResult<Customer> = try {
        val response = apiService.getCustomer(id)
        if (response.isSuccessful) NetworkResult.Success(response.body()!!)
        else NetworkResult.Failure("Error ${response.code()}: ${response.message()}")
    } catch (e: Exception) {
        NetworkResult.Failure(e.message ?: "Unexpected error")
    }

    suspend fun updateCustomer(customer: Customer): NetworkResult<Customer> = try {
        val response = apiService.updateCustomer(customer.id, customer)
        if (response.isSuccessful) NetworkResult.Success(response.body()!!)
        else NetworkResult.Failure("Error ${response.code()}: ${response.message()}")
    } catch (e: Exception) {
        NetworkResult.Failure(e.message ?: "Unexpected error")
    }

    suspend fun getBookings(customerId: Int): NetworkResult<List<Booking>> = try {
        val response = apiService.getBookingsByCustomer(customerId)
        if (response.isSuccessful) NetworkResult.Success(response.body() ?: emptyList())
        else NetworkResult.Failure("Error ${response.code()}: ${response.message()}")
    } catch (e: Exception) {
        NetworkResult.Failure(e.message ?: "Unexpected error")
    }

    suspend fun updateBooking(booking: Booking): NetworkResult<Booking> = try {
        val response = apiService.updateBooking(booking.bookingNo, booking)
        if (response.isSuccessful) NetworkResult.Success(response.body()!!)
        else NetworkResult.Failure("Error ${response.code()}: ${response.message()}")
    } catch (e: Exception) {
        NetworkResult.Failure(e.message ?: "Unexpected error")
    }

    suspend fun getBooking(bookingNo: String): NetworkResult<Booking> = try {
        val response = apiService.getBookingById(bookingNo)
        if (response.isSuccessful && response.body() != null) {
            NetworkResult.Success(response.body()!!)
        } else {
            NetworkResult.Failure("Error ${response.code()}: ${response.message()}")
        }
    } catch (e: Exception) {
        NetworkResult.Failure(e.localizedMessage ?: "Unexpected error")
    }

}
