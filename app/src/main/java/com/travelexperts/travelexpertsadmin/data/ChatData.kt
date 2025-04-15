package com.travelexperts.travelexpertsadmin.data

data class ChatMessage(
    val id: Long? = null,
    val senderId: String,
    val recipientId: String,
    val content: String,
    val timestamp: String? = null
)

data class ChatInteractionDTO(
    val otherUserId: String,
    val name: String,
    val profilePicture: String?,
    val lastMessage: String,
    val isUserTheLastSender: Boolean
)

data class ChatableUserDTO(
    val userEmail: String,
    val name: String,
    val profilePicture: String?
)

