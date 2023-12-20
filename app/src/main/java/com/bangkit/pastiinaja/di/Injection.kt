package com.bangkit.pastiinaja.di

import android.content.Context
import com.bangkit.pastiinaja.pref.UserPreference
import com.bangkit.pastiinaja.repository.UserRepository
import com.bangkit.pastiinaja.retrofit.ApiConfig
import kotlinx.coroutines.runBlocking

object Injection {
    fun provideRepository(context: Context): UserRepository {
        val pref = UserPreference.getInstance(context.dataStore)
        val user = runBlocking { pref.getSession().first() }

        val database = StoryDatabase.getDatabase(context)

        val apiService = ApiConfig.getApiService(user.token)
        return UserRepository.getInstance(apiService, pref, database)

    }
}