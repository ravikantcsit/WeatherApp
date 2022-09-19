package com.ravikant.weather.weather_condition.data.remote.models

import com.ravikant.weather.weather_condition.data.local.entity.WeatherInfoEntity

data class WeatherDto(
    val current: Current,
    val location: Location
) {
    fun toWeatherInfoEntity(): WeatherInfoEntity {
        return WeatherInfoEntity(
            locationName = location.name,
            temp_f = current.temp_f,
            wind_kph = current.wind_kph,
            conditionText = current.condition.text
        )
    }
}