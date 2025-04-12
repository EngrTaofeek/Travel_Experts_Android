package com.travelexperts.travelexpertsadmin.utils

import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody

fun Any.toJsonRequestBody(): RequestBody {
    val json = Gson().toJson(this)
    return json.toRequestBody("application/json".toMediaType())
}
