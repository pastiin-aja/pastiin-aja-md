package com.bangkit.pastiinaja.ui.login

import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.pastiinaja.data.remote.UserRepository
import kotlinx.coroutines.launch
import retrofit2.HttpException

class LoginViewModel(private val repository: UserRepository): ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun login(email: String, password: String, callback: (Boolean, String) -> Unit) {
        _isLoading.value = true

        viewModelScope.launch {
            try {
                repository.login(email, password).let { response ->
                    if (!response.isError!!) {
                        repository.saveSession(response.data!!)
                        _isLoading.value = false
                        callback(true, "Login Berhasil")
                    } else {
                        _isLoading.value = false
                        callback(false, response.message ?: "Login Gagal")
                    }
                }
            } catch (e: HttpException) {
                _isLoading.value = false
                callback(false, "Login Gagal: ${e.message()}")
            }
        }
    }

}