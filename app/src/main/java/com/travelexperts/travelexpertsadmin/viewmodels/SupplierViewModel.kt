package com.travelexperts.travelexpertsadmin.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.travelexperts.travelexpertsadmin.data.api.repositories.SupplierRepository
import com.travelexperts.travelexpertsadmin.data.api.response.Supplier
import com.travelexperts.travelexpertsadmin.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SupplierViewModel @Inject constructor(
    private val repository: SupplierRepository
) : ViewModel() {

    private val _supplierState = MutableStateFlow<NetworkResult<List<Supplier>>>(NetworkResult.Loading)
    val supplierState: StateFlow<NetworkResult<List<Supplier>>> = _supplierState

    private val _updateState = MutableStateFlow<NetworkResult<Supplier>?>(null)
    val updateState: StateFlow<NetworkResult<Supplier>?> = _updateState

    init {
        fetchSuppliers()
    }

    fun fetchSuppliers() {
        viewModelScope.launch {
            _supplierState.value = NetworkResult.Loading
            _supplierState.value = repository.getSuppliers()
        }
    }

    fun updateSupplier(supplier: Supplier) {
        viewModelScope.launch {
            _updateState.value = NetworkResult.Loading
            _updateState.value = repository.updateSupplier(supplier)
            fetchSuppliers()
        }
    }
}