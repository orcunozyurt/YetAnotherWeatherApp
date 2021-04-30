package com.nerdz.yetanotherweatherapp.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.stream.Collectors

data class Forecast(
    val cod: String,
    val message: Int,
    val cnt: Int,
    val list: List<Data>,
    val city: City
) {
    fun getGroupedData() : List<DailyData> {
        val groupedMap: Map<LocalDate, List<Data>> =
            list.stream().collect(Collectors.groupingBy(Data::getLocalDate, Collectors.toList()))

        return groupedMap.toList()
            .sortedBy { it.first }
            .map { DailyData(Pair(it.first, it.second)) }
    }
}

data class Data(
    val dt: Long,
    val main: Main,
    val weather: List<Weather>,
    val clouds: Clouds,
    val wind: Wind,
    val visibility: Int,
    val pop: Double,
    val sys: Sys,
    val dt_txt: String,
    val rain: Rain?
) : Serializable {
    fun getLocalDateTime() : LocalDateTime {
        return Instant.ofEpochSecond(dt)
            .atZone(ZoneId.systemDefault())
            .toLocalDateTime()
    }

    fun getLocalDateTimeFormatted() : String {
        val date = Instant.ofEpochSecond(dt)
            .atZone(ZoneId.systemDefault())
            .toLocalDate()
        val time = Instant.ofEpochSecond(dt)
            .atZone(ZoneId.systemDefault())
            .toLocalTime()

        return "${date.dayOfMonth} ${date.month.name} - $time"
    }

    fun getLocalDate() : LocalDate {
        return Instant.ofEpochSecond(dt)
            .atZone(ZoneId.systemDefault())
            .toLocalDate()
    }

    fun getFormattedTemp() : String {
        return "${main.temp.toInt()}°"
    }

    fun getFormattedFeel() : String {
        return "${main.feels_like.toInt()}°"
    }

    fun getInterpretation() : String {
        return if(main.temp >= 15) {
            "HOT"
        } else {
            "COLD"
        }
    }

    fun getIconURL() : String {
        return "https://openweathermap.org/img/wn/${weather[0].icon}.png"
    }
}

data class City(
    val id: Int,
    val name: String,
    val coord: Coord,
    val country: String,
    val population: Int,
    val timezone: Int,
    val sunrise: Long,
    val sunset: Long
)

data class Main(
    val temp: Double,
    val feels_like: Double,
    val temp_min: Double,
    val temp_max: Double,
    val pressure: Int,
    val humidity: Int,
)

data class Weather(
    val id: Int,
    val main: String,
    val description: String,
    val icon: String
)

data class Clouds(
    val all: Int
)

data class Wind(
    val speed: Double,
    val deg: Int,
    val gust: Double
)

data class Sys(
    val pod: String
)

data class Rain(
    @SerializedName("3h")
    val threeH: Double
)

data class Coord(
    val lat: Double,
    val lon: Double
)