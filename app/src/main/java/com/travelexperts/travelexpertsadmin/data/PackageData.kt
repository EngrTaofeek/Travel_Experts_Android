package com.travelexperts.travelexpertsadmin.data


data class PackageData(
    val packageId: Int,
    val pkgName: String,
    val pkgStartDate: String,
    val pkgEndDate: String,
    val pkgDesc: String,
    val pkgBasePrice: Double,
    val pkgAgencyCommission: Double,
    val imagePath: String, // Image URL or File Path
    val email: String
)
