package com.bangkit.pastiinaja.ui.login

import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.pastiinaja.data.remote.UserRepository
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: UserRepository): ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun login(email: String, password: String, callback: (Boolean) -> Unit) {
        _isLoading.value = true

        viewModelScope.launch {
            repository.login(email, password).let { response ->
                if (!response.isError!!) {
                    _isLoading.value = false
                    Toast.makeText(null, "Login Berhasil", Toast.LENGTH_SHORT).show()
                    callback(true)
                } else {
                    _isLoading.value = false
                    Toast.makeText(null, "Login Gagal", Toast.LENGTH_SHORT).show()
                    callback(false)
                }
            }
        }
    }

}