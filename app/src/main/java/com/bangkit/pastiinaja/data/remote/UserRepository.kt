package com.bangkit.pastiinaja.data.remote

import com.bangkit.pastiinaja.data.pref.FraudImageBody
import com.bangkit.pastiinaja.data.pref.FraudShareBody
import com.bangkit.pastiinaja.data.pref.FraudTextBody
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

    suspend fun getFraudByFraudId(fraudId: String) = apiService.getFraudByFraudId(fraudId)

    suspend fun postFraudByText(userId: String, text: String) = apiService.postFraudByText(
        FraudTextBody(userId, text)
    )

    suspend fun postFraudByPhoto(userId: String, imageString: String) = apiService.postFraudByPhoto(
        FraudImageBody(userId, imageString)
    )

    suspend fun updateIsShared(fraudId: String) = apiService.updateIsShared(
        FraudShareBody(fraudId, true)
    )
}