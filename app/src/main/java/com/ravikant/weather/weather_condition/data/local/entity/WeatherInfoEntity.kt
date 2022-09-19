package com.ravikant.weather.weather_condition.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ravikant.weather.weather_condition.domain.model.WeatherInfo

@Entity
data class WeatherInfoEntity(
    val locationName: String,
    val temp_f: Double,
    val wind_kph: Double,
    val conditionText: String,
    @PrimaryKey val id: Int? = null
) {
    fun toWeatherInfo(): WeatherInfo {
        return WeatherInfo(
            locationName = locationName,
            temp_f = temp_f,
            wind_kph = wind_kph,
            conditionText = conditionText
        )
    }
}
