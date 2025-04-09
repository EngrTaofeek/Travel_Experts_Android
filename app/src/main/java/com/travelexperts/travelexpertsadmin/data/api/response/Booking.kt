package com.travelexperts.travelexpertsadmin.data.api.response

data class Booking(
    val bookingNo: String,
    val name: String,
    val destination: String,
    val tripStart: String,
    val tripEnd: String,
    val travelerCount: Int,
    val tripTypeId: String,
    val basePrice: Double,
    val agencyCommission: Double,
    val savedAt: String,
    val travelers: String
)
