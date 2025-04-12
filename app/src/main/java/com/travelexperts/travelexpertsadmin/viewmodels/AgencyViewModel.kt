package com.travelexperts.travelexpertsadmin.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.travelexperts.travelexpertsadmin.data.api.repositories.AgencyRepository
import com.travelexperts.travelexpertsadmin.data.api.response.Agency
import com.travelexperts.travelexpertsadmin.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AgencyViewModel @Inject constructor(
    private val repository: AgencyRepository
) : ViewModel() {

    private val _agencies = MutableStateFlow<NetworkResult<List<Agency>>>(NetworkResult.Loading)
    val agencies: StateFlow<NetworkResult<List<Agency>>> = _agencies
    private val _selectedAgency = MutableStateFlow<NetworkResult<Agency>?>(null)
    val selectedAgency: StateFlow<NetworkResult<Agency>?> = _selectedAgency

    fun fetchAgencyById(id: Int) {
        viewModelScope.launch {
            _selectedAgency.value = NetworkResult.Loading
            _selectedAgency.value = repository.getAgencyById(id)
        }
    }


    init {
        fetchAgencies()
    }

    fun fetchAgencies() {
        viewModelScope.launch {
            _agencies.value = NetworkResult.Loading
            _agencies.value = repository.fetchAgencies()
        }
    }
}
