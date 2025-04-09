package com.travelexperts.travelexpertsadmin.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.travelexperts.travelexpertsadmin.data.api.response.Agent
import com.travelexperts.travelexpertsadmin.ui.components.AgentApprovalItem
import com.travelexperts.travelexpertsadmin.utils.NetworkResult
import com.travelexperts.travelexpertsadmin.viewmodels.AgentViewModel
import kotlin.collections.get

@Composable
fun PendingAgentsScreen(navController: NavHostController,viewModel: AgentViewModel = hiltViewModel()) {
    val agentState by viewModel.agentState.collectAsState()

    when (agentState) {
        is NetworkResult.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
        is NetworkResult.Success -> {
            AgentListScreen((agentState as NetworkResult.Success<List<Agent>>).data)
        }
        is NetworkResult.Failure -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = (agentState as NetworkResult.Failure).message, color = Color.Red)
            }
        }
    }


}

@Composable
fun AgentListScreen(agents: List<Agent>) {
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {
        Text("Pending Agent Approvals", style = MaterialTheme.typography.headlineSmall)

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            items(agents.size) { index ->
                AgentApprovalItem(
                    agent = agents[index],
                    onApprove = {
                        //Call viewmodel to update agent
                    },
                    onReject = {
                        //Call viewmodel to update agent
                    }
                )
            }
        }
    }

}
