package com.bangkit.pastiinaja.ui.add

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.pastiinaja.data.remote.UserRepository
import kotlinx.coroutines.launch

class AddViewModel(private val repository: UserRepository): ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun postFraudByText(userId: String, text: String, callback: (Boolean) -> Unit) {
        _isLoading.value = true

        viewModelScope.launch {
            repository.postFraudByText(userId, text).let { response ->
                if (!response.isError!!) {
                    callback(true)
                    Log.d("AddViewModel", "postFraudByText result: ${response.data!![0]!!.result}")
                } else {
                    callback(false)
                    Log.e("AddViewModel", "postFraudByText error: ${response.message}")
                }
                _isLoading.value = false
            }
        }
    }
}