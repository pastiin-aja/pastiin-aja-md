package com.bangkit.pastiinaja.ui.profile

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.bangkit.pastiinaja.data.remote.UserRepository
import com.bangkit.pastiinaja.data.remote.response.FraudItem
import kotlinx.coroutines.launch

class ProfileViewModel (private val repository: UserRepository): ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    val userId: LiveData<String> = repository.getSession().asLiveData()

    private val _listFraud = MutableLiveData<List<FraudItem?>?>()
    val listFraud: LiveData<List<FraudItem?>?> = _listFraud

    fun logout() {
        _isLoading.value = true
        viewModelScope.launch {
            repository.logout()
            _isLoading.value = false
        }
    }

    fun getMyFraud(userId: String) {
        _isLoading.value = true

        viewModelScope.launch {
            repository.getFraudByUserId(userId).let { response ->
                if (!response.isError!!) {
                    _listFraud.value = response.data
                    Log.d("ProfileViewModel", "getMyFraud: ${response.data}")
                }
                _isLoading.value = false
            }
        }
    }

}