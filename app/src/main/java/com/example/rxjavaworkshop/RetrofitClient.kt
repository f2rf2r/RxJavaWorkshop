package com.example.rxjavaworkshop

import io.reactivex.Observable
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

private const val BASE_URL = "https://api-v3.mbta.com/"

fun createRestApi(): RestApi {
    val httpClient = OkHttpClient.Builder().build()
    val rxAdapterFactory = RxJava2CallAdapterFactory.create()
    val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(httpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(rxAdapterFactory)
        .build()
    return retrofit.create(RestApi::class.java)
}

interface RestApi {

    @GET("alerts")
    fun getAlerts(): Observable<AlertResponse>
}

