package com.travelexperts.travelexpertsadmin.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.travelexperts.travelexpertsadmin.data.api.repositories.AgentRepository
import com.travelexperts.travelexpertsadmin.data.api.response.Agent
import com.travelexperts.travelexpertsadmin.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AgentViewModel @Inject constructor(
    private val repository: AgentRepository
) : ViewModel() {

    private val _agentState = MutableStateFlow<NetworkResult<List<Agent>>>(NetworkResult.Loading)
    val agentState: StateFlow<NetworkResult<List<Agent>>> = _agentState

    init {
        fetchAgents()
    }

    fun fetchAgents() {
        viewModelScope.launch {
            _agentState.value = NetworkResult.Loading
            _agentState.value = repository.getAgents()
        }
    }
}