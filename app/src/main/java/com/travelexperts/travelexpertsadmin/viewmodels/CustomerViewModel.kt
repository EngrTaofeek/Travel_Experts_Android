package com.travelexperts.travelexpertsadmin.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.travelexperts.travelexpertsadmin.data.api.repositories.CustomerRepository
import com.travelexperts.travelexpertsadmin.data.api.response.Booking
import com.travelexperts.travelexpertsadmin.data.api.response.Customer
import com.travelexperts.travelexpertsadmin.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CustomerViewModel @Inject constructor(
    private val repository: CustomerRepository
) : ViewModel() {

    private val _customers = MutableStateFlow<NetworkResult<List<Customer>>>(NetworkResult.Loading)
    val customers: StateFlow<NetworkResult<List<Customer>>> = _customers

    private val _selectedCustomer = MutableStateFlow<NetworkResult<Customer>?>(null)
    val selectedCustomer: StateFlow<NetworkResult<Customer>?> = _selectedCustomer

    private val _bookings = MutableStateFlow<NetworkResult<List<Booking>>?>(null)
    val bookings: StateFlow<NetworkResult<List<Booking>>?> = _bookings

    private val _updateCustomerState = MutableStateFlow<NetworkResult<Customer>?>(null)
    val updateCustomerState: StateFlow<NetworkResult<Customer>?> = _updateCustomerState

    private val _updateBookingState = MutableStateFlow<NetworkResult<Booking>?>(null)
    val updateBookingState: StateFlow<NetworkResult<Booking>?> = _updateBookingState

    init {
        fetchCustomers()
    }
    private val _selectedBooking = MutableStateFlow<NetworkResult<Booking>?>(null)
    val selectedBooking: StateFlow<NetworkResult<Booking>?> = _selectedBooking

    fun fetchBookingById(bookingNo: String) = viewModelScope.launch {
        _selectedBooking.value = NetworkResult.Loading
        _selectedBooking.value = repository.getBooking(bookingNo)
    }

    fun fetchCustomers() = viewModelScope.launch {
        _customers.value = NetworkResult.Loading
        _customers.value = repository.getCustomers()
    }

    fun fetchCustomer(id: Int) = viewModelScope.launch {
        _selectedCustomer.value = repository.getCustomer(id)
    }

    fun updateCustomer(customer: Customer) = viewModelScope.launch {
        _updateCustomerState.value = NetworkResult.Loading
        _updateCustomerState.value = repository.updateCustomer(customer)
    }

    fun fetchBookings(customerId: Int) = viewModelScope.launch {
        _bookings.value = NetworkResult.Loading
        _bookings.value = repository.getBookings(customerId)
    }

    fun updateBooking(booking: Booking) = viewModelScope.launch {
        _updateBookingState.value = NetworkResult.Loading
        _updateBookingState.value = repository.updateBooking(booking)
    }
}
