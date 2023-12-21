package com.bangkit.pastiinaja.ui.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.pastiinaja.data.remote.UserRepository
import com.bangkit.pastiinaja.data.remote.response.FraudItem
import com.bangkit.pastiinaja.data.remote.response.FraudPostResponse
import com.bangkit.pastiinaja.data.remote.response.FraudResponse
import kotlinx.coroutines.launch
import retrofit2.HttpException

class DetailViewModel(private val repository: UserRepository): ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _detailStory = MutableLiveData<FraudPostResponse>()
    val detailStory: LiveData<FraudPostResponse> = _detailStory

    fun getDetail(id: String) {
        _isLoading.value = true

        viewModelScope.launch {
            try {
                val response = repository.getFraudByFraudId(id)
                _detailStory.value = response
                _isLoading.value = false
            } catch (e: HttpException) {
                Log.e("DetailViewModel", e.message())
                _isLoading.value = false
            }
        }
    }
}