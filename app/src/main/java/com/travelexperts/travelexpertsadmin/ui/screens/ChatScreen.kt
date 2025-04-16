package com.travelexperts.travelexpertsadmin.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.ui.Alignment

import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import com.travelexperts.travelexpertsadmin.data.ChatMessage
import com.travelexperts.travelexpertsadmin.utils.NetworkResult
import com.travelexperts.travelexpertsadmin.viewmodels.ChatViewModel

@Composable
fun ChatScreen(
    currentUserEmail: String,
    otherUserEmail: String,
    navController: NavController,
    viewModel: ChatViewModel = hiltViewModel()
) {
    val chatHistory by viewModel.chatHistory.collectAsState()
    var newMessage by remember { mutableStateOf("") }

    val messages = viewModel.messages

    LaunchedEffect(Unit) {
        viewModel.fetchChatHistory(currentUserEmail, otherUserEmail)
        viewModel.initWebSocket(currentUserEmail)
    }

    Column(Modifier.fillMaxSize()) {
        Text("Chat with $otherUserEmail", modifier = Modifier.padding(16.dp))

        when (chatHistory) {
            is NetworkResult.Loading -> CircularProgressIndicator(Modifier.align(Alignment.CenterHorizontally))
            is NetworkResult.Success -> {
                val messages = (chatHistory as NetworkResult.Success<List<ChatMessage>>).data.reversed()
                LazyColumn(
                    modifier = Modifier
                        .weight(1f)
                        .padding(16.dp),
                    reverseLayout = true
                ) {
                    items(messages.size) { index ->
                        ChatBubble(
                            message = messages[index].content,
                            isFromMe = messages[index].senderId == currentUserEmail,
                            timestamp = messages[index].timestamp.toString()
                        )
                    }
                }
            }

            is NetworkResult.Failure -> Text("Failed to load chat")
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = newMessage,
                onValueChange = { newMessage = it },
                modifier = Modifier.weight(1f),
                placeholder = { Text("Type a message...") }
            )
            Spacer(modifier = Modifier.width(8.dp))
            Button(onClick = {

                viewModel.sendMessage(ChatMessage(senderId =  currentUserEmail, recipientId =  otherUserEmail, content =  newMessage))
                newMessage = ""
            }) {
                Text("Send")
            }
        }
    }
}

@Composable
fun ChatBubble(message: String, isFromMe: Boolean, timestamp: String) {
    val alignment = if (isFromMe) Arrangement.End else Arrangement.Start
    val bubbleColor = if (isFromMe) MaterialTheme.colorScheme.primary else Color(0xFFE0E0E0)
    val textColor = if (isFromMe) Color.White else Color.Black

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = alignment
    ) {
        Column(horizontalAlignment = if (isFromMe) Alignment.End else Alignment.Start) {
            Box(
                modifier = Modifier
                    .background(bubbleColor, shape = RoundedCornerShape(12.dp))
                    .padding(horizontal = 12.dp, vertical = 8.dp)
            ) {
                Text(text = message, color = textColor)
            }
            Text(
                text = timestamp,
                style = MaterialTheme.typography.labelSmall,
                color = Color.Gray,
                modifier = Modifier.padding(4.dp)
            )
        }
    }
}

