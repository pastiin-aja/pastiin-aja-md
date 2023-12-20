package com.bangkit.pastiinaja.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.bangkit.pastiinaja.pref.UserModel
import com.bangkit.pastiinaja.repository.UserRepository
import com.bangkit.pastiinaja.response.LoginResponse

class LoginViewModel(private val repository: UserRepository) : ViewModel() {
    fun saveSession(user: UserModel) {
        viewModelScope.launch {
            repository.saveSession(user)
        }
    }

    fun login(email: String, pass: String): LiveData<Result<LoginResponse>> =
        repository.login(email, pass)
}