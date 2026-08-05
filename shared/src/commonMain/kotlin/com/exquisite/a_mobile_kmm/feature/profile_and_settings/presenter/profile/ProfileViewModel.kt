package com.exquisite.a_mobile_kmm.feature.profile_and_settings.presenter.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.exquisite.a_mobile_kmm.core.database.datastore.AMobileDataStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class ProfileViewModel(private val dataStore: AMobileDataStore) : ViewModel() {

    private var _customerName = MutableStateFlow("")
    val customerName = _customerName.asStateFlow()

    private var _profilePicture = MutableStateFlow("")
    val profilePicture = _profilePicture.asStateFlow()

    private var _customerEmail = MutableStateFlow("")
    val customerEmail = _customerEmail.asStateFlow()

    init {
        getUserName()
        getProfilePicture()
        getUserEmail()
    }

    fun getUserName() {
        viewModelScope.launch(Dispatchers.IO) {
            _customerName.value = dataStore.getCustomerName().first()
        }
    }

    fun getProfilePicture() {
        viewModelScope.launch(Dispatchers.IO) {
            _profilePicture.value = dataStore.getProfilePicture().first()
        }
    }

    fun getUserEmail() {
        viewModelScope.launch(Dispatchers.IO) {
            _customerEmail.value = dataStore.getUserEmail().first()
        }
    }

}