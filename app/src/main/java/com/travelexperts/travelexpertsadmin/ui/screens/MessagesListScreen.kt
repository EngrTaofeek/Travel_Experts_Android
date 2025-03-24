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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow

@Composable
fun MessagesListScreen(navController: NavController) {
    val customers = listOf(
        ChatCustomer(1, "John Doe", "Thanks, I’ll get back to you.", "12:30 PM"),
        ChatCustomer(2, "Jane Smith", "Can you send the invoice?", "Yesterday"),
        ChatCustomer(3, "Mike Jones", "Got it, thank you!", "Mon")
    )

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Messages", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { /* TODO: Start new chat screen */ },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Start New Chat")
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {
            items(customers.size) { index ->
                ChatCustomerItem(customer = customers[index]) {
                    navController.navigate("chat/${customers[index].id}")
                }
            }
        }
    }
}


@Composable
fun ChatCustomerItem(customer: ChatCustomer, onClick: () -> Unit) {
    Column(modifier = Modifier
        .clickable { onClick() }
        .padding(vertical = 8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = "Avatar",
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.2f))
                    .padding(8.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(customer.name, style = MaterialTheme.typography.bodyLarge)
                Text(
                    customer.lastMessage,
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Text(
                text = customer.lastTimestamp,
                style = MaterialTheme.typography.labelSmall,
                modifier = Modifier.padding(start = 8.dp)
            )
        }

        Divider(modifier = Modifier.padding(top = 8.dp))
    }
}
