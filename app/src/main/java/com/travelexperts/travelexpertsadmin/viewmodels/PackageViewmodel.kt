package com.travelexperts.travelexpertsadmin.viewmodels

import android.content.ContentResolver
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.travelexperts.travelexpertsadmin.data.api.repositories.PackageRepository
import com.travelexperts.travelexpertsadmin.data.api.response.PackageData
import com.travelexperts.travelexpertsadmin.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject

// viewmodel/PackageViewModel.kt
@HiltViewModel
class PackageViewModel @Inject constructor(
    private val repository: PackageRepository
) : ViewModel() {

    private val _packageListState = MutableStateFlow<NetworkResult<List<PackageData>>>(NetworkResult.Loading)
    val packageListState: StateFlow<NetworkResult<List<PackageData>>> = _packageListState

    private val _selectedPackage = MutableStateFlow<NetworkResult<PackageData>?>(null)
    val selectedPackage: StateFlow<NetworkResult<PackageData>?> = _selectedPackage

    private val _updateState = MutableStateFlow<NetworkResult<PackageData>?>(null)
    val updateState: StateFlow<NetworkResult<PackageData>?> = _updateState

    fun fetchAllPackages() {
        viewModelScope.launch {
            _packageListState.value = NetworkResult.Loading
            _packageListState.value = repository.getAllPackages()
        }
    }
    init {
        fetchAllPackages()
    }

    fun fetchPackageById(id: Int) {
        viewModelScope.launch {
            _selectedPackage.value = NetworkResult.Loading
            _selectedPackage.value = repository.getPackageById(id)
        }
    }

    fun updatePackage(pkg: PackageData) {
        viewModelScope.launch {
            _updateState.value = NetworkResult.Loading
            _updateState.value = repository.updatePackage(pkg)
        }
    }

    fun uploadImage(id: Int, uri: Uri, contentResolver: ContentResolver) {
        viewModelScope.launch {
            val inputStream = contentResolver.openInputStream(uri)
            val requestBody = inputStream?.readBytes()?.toRequestBody("image/*".toMediaTypeOrNull())
            requestBody?.let {
                val multipart = MultipartBody.Part.createFormData(
                    name = "image",
                    filename = "upload.jpg",
                    body = it
                )
                repository.uploadPackageImage(id, multipart)
            }
        }
    }

}
