package com.travelexperts.travelexpertsadmin.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.travelexperts.travelexpertsadmin.data.api.repositories.ProductRepository
import com.travelexperts.travelexpertsadmin.data.api.response.Product
import com.travelexperts.travelexpertsadmin.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

// viewmodels/ProductViewModel.kt
@HiltViewModel
class ProductViewModel @Inject constructor(
    private val repository: ProductRepository
) : ViewModel() {

    private val _productState = MutableStateFlow<NetworkResult<List<Product>>>(NetworkResult.Loading)
    val productState: StateFlow<NetworkResult<List<Product>>> = _productState

    private val _updateState = MutableStateFlow<NetworkResult<Product>?>(null)
    val updateState: StateFlow<NetworkResult<Product>?> = _updateState

    init {
        fetchProducts()
    }

    fun fetchProducts() {
        viewModelScope.launch {
            _productState.value = NetworkResult.Loading
            _productState.value = repository.getProducts()
        }
    }

    fun updateProduct(product: Product) {
        viewModelScope.launch {
            _updateState.value = NetworkResult.Loading
            _updateState.value = repository.updateProduct(product)
            fetchProducts()
        }
    }
}