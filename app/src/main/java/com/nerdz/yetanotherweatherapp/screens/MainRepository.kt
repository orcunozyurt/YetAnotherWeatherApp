package com.nerdz.yetanotherweatherapp.screens

import com.nerdz.yetanotherweatherapp.network.Api

class MainRepository(private val apiService: Api) {

    suspend fun getForecast(place: String, units: String, appId: String) = apiService.getForecast(
        place = place, units = units, appId = appId)

}