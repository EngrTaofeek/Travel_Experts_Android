package com.travelexperts.travelexpertsadmin.viewmodels

import android.app.Application
import android.content.ContentResolver
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.travelexperts.travelexpertsadmin.data.api.repositories.AgentRepository
import com.travelexperts.travelexpertsadmin.data.api.repositories.AuthRepository
import com.travelexperts.travelexpertsadmin.data.api.response.Agent
import com.travelexperts.travelexpertsadmin.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AgentViewModel @Inject constructor(
    private val repository: AgentRepository,
    private val authRepository: AuthRepository,
    private val app: Application
) : AndroidViewModel(app){

    private val _agentState = MutableStateFlow<NetworkResult<List<Agent>>>(NetworkResult.Loading)
    val agentState: StateFlow<NetworkResult<List<Agent>>> = _agentState

    private val _agentDetail = MutableStateFlow<NetworkResult<Agent>?>(null)
    val agentDetail: StateFlow<NetworkResult<Agent>?> = _agentDetail

    private val _updateResult = MutableStateFlow<NetworkResult<Agent>?>(null)
    val updateResult: StateFlow<NetworkResult<Agent>?> = _updateResult

    init {
        fetchAgents()
    }

    fun fetchAgentById(id: Int) {
        viewModelScope.launch {
            _agentDetail.value = NetworkResult.Loading
            _agentDetail.value = repository.getAgent(id)
        }
    }

    fun updateAgent(id: Int, agent: Agent, imageUri: Uri?, resolver: ContentResolver, isPendingAgent: Boolean = false) {
        viewModelScope.launch {
            _updateResult.value = NetworkResult.Loading
            _updateResult.value = repository.updateAgent(id, agent, imageUri, resolver)
            if (!isPendingAgent) {
            authRepository.fetchAgentProfileByEmail(agent.agtemail, app)
            }
            fetchAgents()
        }
    }

    fun fetchAgents() {
        viewModelScope.launch {
            _agentState.value = NetworkResult.Loading
            _agentState.value = repository.getAgents()
        }
    }
}