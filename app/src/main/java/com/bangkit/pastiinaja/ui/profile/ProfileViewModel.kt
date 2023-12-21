package com.bangkit.pastiinaja.ui.profile

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.pastiinaja.data.remote.UserRepository
import com.bangkit.pastiinaja.data.remote.response.FraudItem
import kotlinx.coroutines.launch

class ProfileViewModel (private val repository: UserRepository): ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _listFraud = MutableLiveData<List<FraudItem?>?>()
    val listFraud: LiveData<List<FraudItem?>?> = _listFraud

    init {
        getMyFraud()
    }

    fun getMyFraud() {
        _isLoading.value = true

        viewModelScope.launch {
            repository.getFraudByUserId("8c668f417708091521fce0334f0a4b5c8b8bacaa6f4d21192b54fc4e21319d24").let { response ->
                if (!response.isError!!) {
                    _listFraud.value = response.data
                    Log.d("ProfileViewModel", "getMyFraud: ${response.data}")
                }
                _isLoading.value = false
            }
        }
    }

}