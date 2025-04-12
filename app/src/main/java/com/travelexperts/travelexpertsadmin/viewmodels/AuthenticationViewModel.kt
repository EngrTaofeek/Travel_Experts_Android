package com.travelexperts.travelexpertsadmin.viewmodels

import android.content.ContentResolver
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.travelexperts.travelexpertsadmin.data.api.repositories.AgentRepository
import com.travelexperts.travelexpertsadmin.data.api.requests.AgencyId
import com.travelexperts.travelexpertsadmin.data.api.requests.AgentPayload
import com.travelexperts.travelexpertsadmin.data.api.requests.RegisterAgentRequest
import com.travelexperts.travelexpertsadmin.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthenticationViewModel @Inject constructor(
    private val repository: AgentRepository
) : ViewModel() {

    private val _registerState = MutableStateFlow<NetworkResult<Any>?>(null)
    val registerState: StateFlow<NetworkResult<Any>?> = _registerState

    // viewmodels/AuthViewModel.kt
    fun registerAgent(
        firstName: String,
        middleInitial: String?,
        lastName: String,
        phone: String,
        email: String,
        password: String,
        position: String,
        agencyId: Int,
        imageUri: Uri?,
        contentResolver: ContentResolver
    ) {
        val agentPayload = AgentPayload(
            agtfirstname = firstName,
            agtmiddleinitial = middleInitial,
            agtlastname = lastName,
            agtbusphone = phone,
            agtemail = email,
            agtposition = position,
            agencyid = AgencyId(agencyId)
        )
        val request = RegisterAgentRequest(agent = agentPayload, password = password)

        viewModelScope.launch {
            _registerState.value = NetworkResult.Loading
            _registerState.value = repository.registerAgent(request, imageUri, contentResolver)
        }
    }

}
