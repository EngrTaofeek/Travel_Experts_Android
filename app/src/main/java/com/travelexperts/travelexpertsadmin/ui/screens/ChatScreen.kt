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
import com.travelexperts.travelexpertsadmin.data.ChatCustomer
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.ui.Alignment

import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import com.travelexperts.travelexpertsadmin.data.ChatMessage

@Composable
fun ChatScreen(customerId: Int, navController: NavController) {
    val messages = remember {
        mutableStateListOf(
            ChatMessage(1, customerId, "Hi, how can I help you?", "12:00 PM", false),
            ChatMessage(2, 0, "I'm checking on my booking.", "12:01 PM", true),
            ChatMessage(3, customerId, "Sure, I’ll look it up now.", "12:02 PM", false)
        ).reversed().toMutableStateList()
    }

    var newMessage by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize()) {
        Text("Chat with Customer #$customerId")

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .padding(16.dp),
            reverseLayout = true
        ) {
            items(messages.size) { index ->
                ChatBubble(message = messages[index])
                Spacer(modifier = Modifier.height(8.dp))
            }
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
                if (newMessage.isNotBlank()) {
                    messages.add(
                        ChatMessage(
                            id = messages.size + 1,
                            senderId = 0,
                            message = newMessage,
                            timestamp = "Now",
                            isFromMe = true
                        )
                    )
                    newMessage = ""
                }
            }) {
                Text("Send")
            }
        }
    }
}

@Composable
fun ChatBubble(message: ChatMessage) {
    val alignment = if (message.isFromMe) Arrangement.End else Arrangement.Start
    val bubbleColor = if (message.isFromMe) MaterialTheme.colorScheme.primary else Color(0xFFE0E0E0)
    val textColor = if (message.isFromMe) Color.White else Color.Black

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        horizontalArrangement = alignment
    ) {
        Column(horizontalAlignment = if (message.isFromMe) Alignment.End else Alignment.Start) {
            Box(
                modifier = Modifier
                    .background(bubbleColor, shape = RoundedCornerShape(12.dp))
                    .padding(horizontal = 12.dp, vertical = 8.dp)
            ) {
                Text(text = message.message, color = textColor)
            }
            Text(
                text = message.timestamp,
                style = MaterialTheme.typography.labelSmall,
                color = Color.Gray,
                modifier = Modifier.padding(4.dp)
            )
        }
    }
}

