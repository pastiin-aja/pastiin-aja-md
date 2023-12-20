package com.bangkit.pastiinaja.data.remote.retrofit

import com.bangkit.pastiinaja.data.remote.response.FraudResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("all-shared-fraud")
    suspend fun getAllFraud(): FraudResponse

    @GET("fraud-by-user-id/{userId}")
    suspend fun getFraudByUserId(
        @Path("userId") userId: String
    ): FraudResponse

}