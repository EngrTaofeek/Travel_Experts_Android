package com.travelexperts.travelexpertsadmin.data

data class Agent(
    val id: Int,
    val name: String,
    val email: String,
    var status: String = "Pending" // Can be "Pending", "Approved", or "Rejected"
)
