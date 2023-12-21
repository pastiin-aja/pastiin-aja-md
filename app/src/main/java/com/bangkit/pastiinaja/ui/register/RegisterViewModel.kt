package com.bangkit.pastiinaja.ui.register

import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.pastiinaja.data.remote.UserRepository
import kotlinx.coroutines.launch
import retrofit2.HttpException

class RegisterViewModel(private val repository: UserRepository): ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun register(name: String, email: String, password: String, callback: (Boolean, String) -> Unit) {
        _isLoading.value = true

        viewModelScope.launch {
            try {
                repository.register(name, email, password).let { response ->
                    if (!response.isError!!) {
                        _isLoading.value = false
                        callback(true, "Register Berhasil")
                    } else {
                        _isLoading.value = false
                        callback(false, response.message ?: "Register Gagal")
                    }
                }
            } catch (e: HttpException) {
                _isLoading.value = false
                callback(false, "Register Gagal: ${e.message()}")
            }
        }
    }

}