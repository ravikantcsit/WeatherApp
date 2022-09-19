package com.ravikant.weather.weather_condition.domain.model

data class WeatherInfo(
    val locationName: String,
    val temp_f: Double,
    val wind_kph: Double,
    val conditionText: String
)
