package com.travelexperts.travelexpertsadmin.data.api

import com.travelexperts.travelexpertsadmin.data.api.response.Agent
import com.travelexperts.travelexpertsadmin.data.api.response.Booking
import com.travelexperts.travelexpertsadmin.data.api.response.Customer
import com.travelexperts.travelexpertsadmin.data.api.response.Product
import com.travelexperts.travelexpertsadmin.data.api.response.Supplier
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiService {
    @GET("api/agents")
    suspend fun getAgents(): Response<List<Agent>>

    @GET("api/supplier/list")
    suspend fun getSuppliers(): Response<List<Supplier>>

    @PUT("api/supplier/{id}")
    suspend fun updateSupplier(
        @Path("id") id: Int,
        @Body supplier: Supplier
    ): Response<Supplier>

    @GET("api/product/list")
    suspend fun getProducts(): Response<List<Product>>

    @PUT("api/product/{id}")
    suspend fun updateProduct(@Path("id") id: Int, @Body product: Product): Response<Product>

    @GET("api/customer")
    suspend fun getAllCustomers(): Response<List<Customer>>

    @GET("api/customer/{id}")
    suspend fun getCustomer(@Path("id") id: Int): Response<Customer>

    @PUT("api/customer/{id}")
    suspend fun updateCustomer(
        @Path("id") id: Int,
        @Body customer: Customer
    ): Response<Customer>

    @GET("api/booking/customer/{customerId}")
    suspend fun getBookingsByCustomer(@Path("customerId") customerId: Int): Response<List<Booking>>

    @PUT("api/booking/{bookingNo}")
    suspend fun updateBooking(
        @Path("bookingNo") bookingNo: String,
        @Body booking: Booking
    ): Response<Booking>

    @GET("api/booking/{bookingNo}")
    suspend fun getBookingById(@Path("bookingNo") bookingNo: String): Response<Booking>


}