package com.travelexperts.travelexpertsadmin.data.api.response

// data/model/Package.kt
data class PackageData(
    val id: Int,
    val pkgname: String,
    val pkgstartdate: String,
    val pkgenddate: String,
    val pkgdesc: String,
    val pkgbaseprice: Double,
    val pkgagencycommission: Double,
    val imageUrl: String?,
    val destination: String?,
    val lat: Double?,
    val lng: Double?,
    val rating: Double?,
    val reviews: List<Review> = emptyList()
)


