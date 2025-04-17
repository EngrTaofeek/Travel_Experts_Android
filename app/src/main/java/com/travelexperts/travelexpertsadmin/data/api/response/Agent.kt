package com.travelexperts.travelexpertsadmin.data.api.response



data class Agent(
    val id: Int,
    val agtfirstname: String,
    val agtmiddleinitial: String?,
    val agtlastname: String,
    val agtbusphone: String,
    val agtemail: String,
    val agtposition: String,
    val agencyid: Agency,
    var status: String,
    val profileImageUrl: String?,
    val role: String
)