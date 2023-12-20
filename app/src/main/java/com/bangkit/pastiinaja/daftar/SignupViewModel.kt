package com.bangkit.pastiinaja.daftar

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.bangkit.pastiinaja.repository.UserRepository
import com.bangkit.pastiinaja.response.RegisterResponse
import com.bangkit.pastiinaja.response.Result

class SignupViewModel (private val repository: UserRepository) : ViewModel() {
    fun register(name: String, email: String, pass: String): LiveData<Result<RegisterResponse>> =
        repository.register(name, email, pass)
}