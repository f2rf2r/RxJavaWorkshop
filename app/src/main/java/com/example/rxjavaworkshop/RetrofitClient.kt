package com.example.rxjavaworkshop

import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

private const val BASE_URL = "https://api-v3.mbta.com/"

fun createRestApi(): RestApi {
    val httpClient = OkHttpClient.Builder().build()
    val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(httpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    return retrofit.create(RestApi::class.java)
}

interface RestApi {

    @GET("alerts")
    fun getAlerts(): Call<AlertResponse>
}

