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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.travelexperts.travelexpertsadmin.data.ChatInteractionDTO
import com.travelexperts.travelexpertsadmin.datastore.UserPreferences
import com.travelexperts.travelexpertsadmin.ui.navigation.Routes
import com.travelexperts.travelexpertsadmin.utils.NetworkResult
import com.travelexperts.travelexpertsadmin.viewmodels.ChatViewModel

@Composable
fun MessagesListScreen(
    navController: NavController,
    viewModel: ChatViewModel = hiltViewModel(),

) {
    val context = LocalContext.current
    val interactions by viewModel.interactions.collectAsState()

    val userEmail by UserPreferences.getEmail(context).collectAsState(initial = null)


    LaunchedEffect(userEmail) {
        userEmail?.let { id ->
            viewModel.fetchInteractions(userEmail!!)
        }
    }

    Column(Modifier.fillMaxSize().padding(16.dp)) {
        Text("Chats", style = MaterialTheme.typography.headlineMedium)

        Spacer(Modifier.height(16.dp))

        Button(
            onClick = {
                navController.navigate("${Routes.NEW_CHAT}/${userEmail}")

            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Start New Chat")
        }

        Spacer(Modifier.height(16.dp))

        when (interactions) {
            is NetworkResult.Loading -> CircularProgressIndicator()
            is NetworkResult.Success -> {
                val list = (interactions as NetworkResult.Success<List<ChatInteractionDTO>>).data
                LazyColumn {
                    items(list.size) { index ->
                        ChatCustomerItem(
                            name = list[index].name,
                            lastMessage = list[index].lastMessage ?: "",
                            onClick = {
                                navController.navigate("${Routes.CHAT}/${userEmail}/${list[index].otherUserId}")
                            }
                        )
                    }
                }
            }
            is NetworkResult.Failure -> Text("Error: ${(interactions as NetworkResult.Failure).message}")
        }
    }
}

@Composable
fun ChatCustomerItem(name: String, lastMessage: String, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .clickable { onClick() }
            .padding(vertical = 8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = null,
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.2f))
                    .padding(8.dp)
            )

            Spacer(Modifier.width(12.dp))

            Column {
                Text(name, style = MaterialTheme.typography.bodyLarge)
                Text(lastMessage, style = MaterialTheme.typography.bodySmall, maxLines = 1)
            }
        }

        Spacer(Modifier.height(8.dp))

        Divider()
    }
}

