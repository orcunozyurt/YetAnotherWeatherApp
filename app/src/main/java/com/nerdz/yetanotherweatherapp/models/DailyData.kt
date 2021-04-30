package com.nerdz.yetanotherweatherapp.models

import java.time.LocalDate

data class DailyData(
    var dailydata: Pair<LocalDate, List<Data>>
) {
    fun getMaxTemp() : String {
        val dataList = dailydata.second
        val maxTemp = dataList.maxOf { it.main.temp_max }.toInt()
        return "$maxTemp°"
    }

    fun getMinTemp() : String {
        val dataList = dailydata.second
        val minTemp =  dataList.minOf { it.main.temp_min }.toInt()
        return "$minTemp°"
    }
}
