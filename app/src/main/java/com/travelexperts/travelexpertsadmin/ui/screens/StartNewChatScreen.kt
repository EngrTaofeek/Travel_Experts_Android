package com.travelexperts.travelexpertsadmin.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.travelexperts.travelexpertsadmin.data.ChatableUserDTO
import com.travelexperts.travelexpertsadmin.datastore.UserPreferences
import com.travelexperts.travelexpertsadmin.ui.navigation.Routes
import com.travelexperts.travelexpertsadmin.utils.NetworkResult
import com.travelexperts.travelexpertsadmin.viewmodels.ChatViewModel

@Composable
fun StartNewChatScreen(
    currentUserEmail: String,
    navController: NavController,
    viewModel: ChatViewModel = hiltViewModel(),
) {
    val contacts by viewModel.contacts.collectAsState()
    val context = LocalContext.current
    val userIdString by UserPreferences.getUserId(context).collectAsState(initial = null)

    LaunchedEffect(userIdString) {
        userIdString?.toIntOrNull()?.let { id ->
            viewModel.fetchContacts(id, "agent")
        }
    }


    Column(Modifier.fillMaxSize().padding(16.dp)) {
        Text("Select Contact", style = MaterialTheme.typography.headlineMedium)
        Spacer(Modifier.height(16.dp))

        when (contacts) {
            is NetworkResult.Loading -> CircularProgressIndicator()
            is NetworkResult.Success -> {
                val list = (contacts as NetworkResult.Success<List<ChatableUserDTO>>).data
                LazyColumn {
                    items(list.size) { index ->
                        ChatCustomerItem(
                            name = list[index].name,
                            lastMessage = list[index].userEmail,
                            onClick = {
                                navController.navigate("${Routes.CHAT}/${currentUserEmail}/${list[index].userEmail}")

                            }
                        )
                    }
                }
            }
            is NetworkResult.Failure -> Text("Failed: ${(contacts as NetworkResult.Failure).message}")
        }
    }
}
