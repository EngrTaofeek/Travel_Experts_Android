package com.travelexperts.travelexpertsadmin.viewmodels

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.travelexperts.travelexpertsadmin.data.ChatInteractionDTO
import com.travelexperts.travelexpertsadmin.data.ChatMessage
import com.travelexperts.travelexpertsadmin.data.ChatableUserDTO
import com.travelexperts.travelexpertsadmin.data.api.repositories.ChatRepository
import com.travelexperts.travelexpertsadmin.utils.NetworkResult
import com.travelexperts.travelexpertsadmin.websocket.WebSocketManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val repository: ChatRepository,
    private val webSocketManager: WebSocketManager
) : ViewModel() {

    private val _interactions = MutableStateFlow<NetworkResult<List<ChatInteractionDTO>>>(NetworkResult.Loading)
    val interactions: StateFlow<NetworkResult<List<ChatInteractionDTO>>> = _interactions

    private val _contacts = MutableStateFlow<NetworkResult<List<ChatableUserDTO>>>(NetworkResult.Loading)
    val contacts: StateFlow<NetworkResult<List<ChatableUserDTO>>> = _contacts

    private val _chatHistory = MutableStateFlow<NetworkResult<List<ChatMessage>>>(NetworkResult.Loading)
    val chatHistory: StateFlow<NetworkResult<List<ChatMessage>>> = _chatHistory
    private val _messages = mutableStateListOf<ChatMessage>()
    val messages: List<ChatMessage> get() = _messages


    fun fetchInteractions(userId: String) {
        viewModelScope.launch {
            _interactions.value = NetworkResult.Loading
            _interactions.value = repository.getInteractions(userId)
        }
    }

    fun fetchContacts(userId: Int, role: String) {
        viewModelScope.launch {
            _contacts.value = NetworkResult.Loading
            _contacts.value = repository.getContacts(userId, role)
        }
    }

    fun fetchChatHistory(user1: String, user2: String) {
        viewModelScope.launch {
            _chatHistory.value = NetworkResult.Loading
            _chatHistory.value = repository.getChatHistory(user1, user2)
        }
    }
    fun initWebSocket(userEmail: String) {
        webSocketManager.connect(userEmail) { message ->
            _messages.add(message)
        }
    }

    fun sendMessage(message: ChatMessage) {
        webSocketManager.sendMessage(message)
        _messages.add(message.copy()) // Optional local echo
        viewModelScope.launch {
            delay(500) // ⏳ optional delay to wait for backend save
            fetchChatHistory(message.senderId, message.recipientId)
        }
    }

    override fun onCleared() {
        webSocketManager.disconnect()
        super.onCleared()
    }
}
