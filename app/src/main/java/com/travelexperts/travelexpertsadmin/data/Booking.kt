package com.travelexperts.travelexpertsadmin.data

data class Booking(
    val bookingId: Int,
    var bookingDate: String,
    var bookingNo: String?,
    var travelerCount: Double,
    var customerId: Int,
    var tripTypeId: String?,
    var packageId: Int?
)
