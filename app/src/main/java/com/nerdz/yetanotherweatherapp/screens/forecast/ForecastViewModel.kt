package com.nerdz.yetanotherweatherapp.screens.forecast

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.nerdz.yetanotherweatherapp.network.Resource
import com.nerdz.yetanotherweatherapp.screens.MainRepository
import kotlinx.coroutines.Dispatchers

class ForecastViewModel(private val repository: MainRepository) : ViewModel() {

    fun getForecast(place: String, units: String, appId: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = repository.getForecast(place = place, units = units, appId = appId)))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
        }
    }


}