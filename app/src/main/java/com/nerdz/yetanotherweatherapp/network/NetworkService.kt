package com.nerdz.yetanotherweatherapp.network

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetworkService {

    private const val BASE_URL = "https://api.openweathermap.org/data/2.5/"

    private val logging = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    private val client: OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(logging)
        .build()

    private val gsonConfiguration = GsonBuilder()
        .setPrettyPrinting()
        .create()

    private var retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gsonConfiguration))
            .build()

    fun <S> createService(serviceClass: Class<S>): S {
        return  retrofit.create(serviceClass)
    }
}