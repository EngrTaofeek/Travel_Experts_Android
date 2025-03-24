package com.travelexperts.travelexpertsadmin.data

data class ChatCustomer(
    val id: Int,
    val name: String,
    val lastMessage: String,
    val lastTimestamp: String
)

data class ChatMessage(
    val id: Int,
    val senderId: Int,
    val message: String,
    val timestamp: String,
    val isFromMe: Boolean
)
