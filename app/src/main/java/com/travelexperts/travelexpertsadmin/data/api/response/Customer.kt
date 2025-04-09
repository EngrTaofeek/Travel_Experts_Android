package com.travelexperts.travelexpertsadmin.data.api.response

data class Customer(
    val id: Int,
    val custfirstname: String,
    val custlastname: String,
    val custaddress: String,
    val custcity: String,
    val custprov: String,
    val custpostal: String,
    val custcountry: String,
    val custhomephone: String?,
    val custbusphone: String,
    val custemail: String,
    val profileImageUrl: String? = null
)
