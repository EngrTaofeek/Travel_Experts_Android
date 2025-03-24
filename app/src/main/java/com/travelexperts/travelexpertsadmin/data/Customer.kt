package com.travelexperts.travelexpertsadmin.data

data class Customer(
    val customerId: Int,
    var custFirstName: String,
    var custLastName: String,
    var custAddress: String,
    var custCity: String,
    var custProv: String,
    var custPostal: String,
    var custCountry: String?,
    var custHomePhone: String?,
    var custBusPhone: String,
    var custEmail: String,
    var agentId: Int?
)
