package com.bangkit.pastiinaja.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.pastiinaja.data.remote.UserRepository
import com.bangkit.pastiinaja.data.remote.response.FraudItem
import com.bangkit.pastiinaja.data.remote.retrofit.ApiConfig
import kotlinx.coroutines.launch

class MainViewModel(private val repository: UserRepository): ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _listFraud = MutableLiveData<List<FraudItem?>?>()
    val listFraud: LiveData<List<FraudItem?>?> = _listFraud

    init {
        getAllFraud()
    }

    fun getAllFraud() {
        _isLoading.value = true

        viewModelScope.launch {
            repository.getAllFraud().let { response ->
                if (!response.isError!!) {
                    _listFraud.value = response.data
                    Log.d("MainViewModel", "getAllFraud: ${response.data}")
                }
                _isLoading.value = false
            }
        }
    }

}