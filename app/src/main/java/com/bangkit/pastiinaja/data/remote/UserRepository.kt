package com.bangkit.pastiinaja.data.remote

import com.bangkit.pastiinaja.data.pref.UserPreference
import com.bangkit.pastiinaja.data.remote.response.LoginData
import com.bangkit.pastiinaja.data.remote.retrofit.ApiService
import kotlinx.coroutines.flow.Flow

class UserRepository private constructor(
    private val apiService: ApiService,
    private val userPreference: UserPreference
) {
    companion object {
        @Volatile
        private var instance: UserRepository? = null

        fun getInstance(apiService: ApiService, userPreference: UserPreference): UserRepository =
            instance ?: synchronized(this) {
                instance ?: UserRepository(apiService, userPreference)
            }
    }

    suspend fun saveSession(loginData: LoginData) {
        userPreference.saveSession(loginData)
    }

    fun getSession(): Flow<String> {
        return userPreference.getSession()
    }

    suspend fun logout() {
        userPreference.logout()
    }
    suspend fun register(name: String, email: String, password: String) = apiService.register(name, email, password)

    suspend fun login(email: String, password: String) = apiService.login(email, password)

    suspend fun getAllFraud() = apiService.getAllFraud()

    suspend fun getFraudByUserId(userId: String) = apiService.getFraudByUserId(userId)
}