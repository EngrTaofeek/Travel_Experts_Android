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
import androidx.navigation.NavHostController
import com.travelexperts.travelexpertsadmin.data.Agent
import com.travelexperts.travelexpertsadmin.ui.components.AgentApprovalItem

@Composable
fun PendingAgentsScreen(navController: NavHostController) {
    var agents by remember {
        mutableStateOf(
            listOf(
                Agent(1, "Sarah Connor", "sarah@example.com"),
                Agent(2, "James Bond", "james.bond@mi6.gov"),
                Agent(3, "Lara Croft", "lara@tombraider.com")
            ).filter { it.status == "Pending" }
        )
    }

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
                        agents = agents.map {
                            if (it.id == agents[index].id) it.copy(status = "Approved") else it
                        }
                    },
                    onReject = {
                        agents = agents.map {
                            if (it.id == agents[index].id) it.copy(status = "Rejected") else it
                        }
                    }
                )
            }
        }
    }
}
