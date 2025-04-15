package com.travelexperts.travelexpertsadmin.data.api

import com.travelexperts.travelexpertsadmin.data.ChatInteractionDTO
import com.travelexperts.travelexpertsadmin.data.ChatMessage
import com.travelexperts.travelexpertsadmin.data.ChatableUserDTO
import com.travelexperts.travelexpertsadmin.data.api.response.Agency
import com.travelexperts.travelexpertsadmin.data.api.response.Agent
import com.travelexperts.travelexpertsadmin.data.api.response.Booking
import com.travelexperts.travelexpertsadmin.data.api.response.Customer
import com.travelexperts.travelexpertsadmin.data.api.response.LoginResponse
import com.travelexperts.travelexpertsadmin.data.api.response.PackageData
import com.travelexperts.travelexpertsadmin.data.api.response.Product
import com.travelexperts.travelexpertsadmin.data.api.response.Supplier
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("api/agents")
    suspend fun getAgents(): Response<List<Agent>>

    @Multipart
    @POST("api/agents")
    suspend fun registerAgent(
        @Part("agent") agentJson: RequestBody, // JSON stringified RegisterAgentRequest
        @Part image: MultipartBody.Part?
    ): Response<Any>

    @GET("api/agents/{id}")
    suspend fun getAgent(@Path("id") id: Int): Response<Agent>

    @FormUrlEncoded
    @POST("api/user/login-agent")
    suspend fun loginUser(
        @Field("email") email: String,
        @Field("password") password: String
    ): Response<LoginResponse>

    @GET("api/chat/interactions")
    suspend fun getInteractions(@Query("userId") userId: String): Response<List<ChatInteractionDTO>>

    @GET("api/chat/contacts")
    suspend fun getContacts(@Query("userId") userId: Int, @Query("role") role: String): Response<List<ChatableUserDTO>>

    @GET("api/chat/history/{user1}/{user2}")
    suspend fun getChatHistory(@Path("user1") user1: String, @Path("user2") user2: String): Response<List<ChatMessage>>



    @GET("api/agents/email")
    suspend fun getAgentByEmail(
        @Query("email") email: String
    ): Response<Agent>




    @Multipart
    @PUT("api/agents/{id}")
    suspend fun updateAgent(
        @Path("id") id: Int,
        @Part("agent") agent: RequestBody,
        @Part image: MultipartBody.Part?
    ): Response<Agent>

    @GET("api/agencies/{id}")
    suspend fun getAgencyById(@Path("id") id: Int): Response<Agency>

    @GET("api/agencies")
    suspend fun getAgencies(): Response<List<Agency>>



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

    @GET("api/package")
    suspend fun getAllPackages(): Response<List<PackageData>>

    @GET("api/package/{id}")
    suspend fun getPackageById(@Path("id") id: Int): Response<PackageData>

    @PUT("api/package/{id}")
    suspend fun updatePackage(
        @Path("id") id: Int,
        @Body pkg: PackageData
    ): Response<PackageData>

    @Multipart
    @POST("api/package/{id}/image")
    suspend fun uploadPackageImage(
        @Path("id") id: Int,
        @Part image: MultipartBody.Part
    ): Response<Unit>




}