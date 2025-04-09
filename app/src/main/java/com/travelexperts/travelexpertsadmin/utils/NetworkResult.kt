package com.travelexperts.travelexpertsadmin.utils

sealed class NetworkResult<out T> {
    object Loading : NetworkResult<Nothing>()
    data class Success<T>(val data: T) : NetworkResult<T>()
    data class Failure(val message: String) : NetworkResult<Nothing>()
}