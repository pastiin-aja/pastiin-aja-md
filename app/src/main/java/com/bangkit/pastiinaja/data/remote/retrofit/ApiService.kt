package com.bangkit.pastiinaja.data.remote.retrofit

import com.bangkit.pastiinaja.data.pref.FraudImageBody
import com.bangkit.pastiinaja.data.pref.FraudTextBody
import com.bangkit.pastiinaja.data.remote.response.FraudPostResponse
import com.bangkit.pastiinaja.data.remote.response.FraudResponse
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
    @GET("all-shared-fraud")
    suspend fun getAllFraud(): FraudResponse

    @GET("fraud-by-user-id/{userId}")
    suspend fun getFraudByUserId(
        @Path("userId") userId: String
    ): FraudResponse


    @POST("fraud-by-text")
    suspend fun postFraudByText(
        @Body body: FraudTextBody
    ): FraudPostResponse

    @POST("fraud-by-photo")
    suspend fun postFraudByPhoto(
        @Body body: FraudImageBody
    ): FraudPostResponse

}