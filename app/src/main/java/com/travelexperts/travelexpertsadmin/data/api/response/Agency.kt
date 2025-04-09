package com.travelexperts.travelexpertsadmin.data.api.response

data class Agency(
    val id: Int,
    val agncyaddress: String,
    val agncycity: String,
    val agncyprov: String,
    val agncypostal: String,
    val agncycountry: String,
    val agncyphone: String,
    val agncyfax: String
)