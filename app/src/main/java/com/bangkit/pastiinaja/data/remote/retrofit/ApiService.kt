package com.bangkit.pastiinaja.data.remote.retrofit

import com.bangkit.pastiinaja.data.remote.response.FraudResponse
import com.bangkit.pastiinaja.data.remote.response.LoginResponse
import com.bangkit.pastiinaja.data.remote.response.RegisterResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {

    @FormUrlEncoded
    @POST("register")
    suspend fun register(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): RegisterResponse

    @FormUrlEncoded
    @POST("login")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): LoginResponse

    @GET("all-shared-fraud")
    suspend fun getAllFraud(): FraudResponse

    @GET("fraud-by-user-id/{userId}")
    suspend fun getFraudByUserId(
        @Path("userId") userId: String
    ): FraudResponse

}