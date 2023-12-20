package com.bangkit.pastiinaja.data.remote

import com.bangkit.pastiinaja.data.remote.retrofit.ApiService

class  UserRepository private constructor(
    private val apiService: ApiService
) {
    companion object {
        @Volatile
        private var instance: UserRepository? = null

        fun getInstance(apiService: ApiService): UserRepository =
            instance ?: synchronized(this) {
                instance ?: UserRepository(apiService)
            }
    }

    suspend fun getAllFraud() = apiService.getAllFraud()

    suspend fun getFraudByUserId(userId: String) = apiService.getFraudByUserId(userId)
}