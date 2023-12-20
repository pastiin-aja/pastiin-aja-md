package com.bangkit.pastiinaja.di

import android.content.Context
import com.bangkit.pastiinaja.data.remote.UserRepository
import com.bangkit.pastiinaja.data.remote.retrofit.ApiConfig

object Injection {
    fun provideRepository(context: Context): UserRepository {
//        val pref = UserPreference.getInstance(context.dataStore)
        val apiService = ApiConfig.getApiService()
        return UserRepository.getInstance(apiService)
    }
}