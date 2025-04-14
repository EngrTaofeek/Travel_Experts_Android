package com.travelexperts.travelexpertsadmin.viewmodels

import android.app.Application
import android.content.ContentResolver
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.travelexperts.travelexpertsadmin.data.api.repositories.AgentRepository
import com.travelexperts.travelexpertsadmin.data.api.repositories.AuthRepository
import com.travelexperts.travelexpertsadmin.data.api.requests.AgencyId
import com.travelexperts.travelexpertsadmin.data.api.requests.AgentPayload
import com.travelexperts.travelexpertsadmin.data.api.requests.RegisterAgentRequest
import com.travelexperts.travelexpertsadmin.datastore.UserPreferences
import com.travelexperts.travelexpertsadmin.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthenticationViewModel @Inject constructor(
    private val repository: AgentRepository,
    private val authRepository: AuthRepository,
    private val app: Application
) : AndroidViewModel(app){

    private val _registerState = MutableStateFlow<NetworkResult<Any>?>(null)
    val registerState: StateFlow<NetworkResult<Any>?> = _registerState
    private val _loginState = MutableStateFlow<NetworkResult<String>?>(null)
    val loginState: StateFlow<NetworkResult<String>?> = _loginState

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _loginState.value = NetworkResult.Loading
            val result = authRepository.login(email, password)


            if (result is NetworkResult.Success) {
                UserPreferences.setBearerToken(app, result.data)
                UserPreferences.setEmail(app, email)
                UserPreferences.setLoggedIn(app, true)

                authRepository.fetchAgentProfileByEmail(email, app)
            }
            _loginState.value = result
        }
    }

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
