package com.nerdz.yetanotherweatherapp.network


import com.nerdz.yetanotherweatherapp.models.Forecast
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {

    @GET("forecast?")
    suspend fun getForecast(
        @Query("q") place: String,
        @Query("units") units: String,
        @Query("appid") appId: String,
    ): Forecast

}