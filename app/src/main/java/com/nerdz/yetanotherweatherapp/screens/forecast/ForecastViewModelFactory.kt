package com.nerdz.yetanotherweatherapp.screens.forecast

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nerdz.yetanotherweatherapp.screens.MainRepository


class ForecastViewModelFactory(private val repository: MainRepository): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = ForecastViewModel(repository) as T
}