package com.bangkit.pastiinaja.ui.result

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.pastiinaja.data.remote.UserRepository
import com.bangkit.pastiinaja.data.remote.response.FraudPostResponse
import kotlinx.coroutines.launch
import retrofit2.HttpException

class ResultViewModel(private val repository: UserRepository): ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _detailResult = MutableLiveData<FraudPostResponse>()
    val detailResult: LiveData<FraudPostResponse> = _detailResult

    fun getResult(id: String) {
        _isLoading.value = true

        viewModelScope.launch {
            try {
                val response = repository.getFraudByFraudId(id)
                _detailResult.value = response
                _isLoading.value = false
            } catch (e: HttpException) {
                Log.e("ResultViewModel", e.message())
                _isLoading.value = false
            }
        }
    }
}