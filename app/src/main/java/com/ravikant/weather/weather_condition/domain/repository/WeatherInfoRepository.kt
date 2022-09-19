package com.ravikant.weather.weather_condition.domain.repository

import com.ravikant.weather.core.util.Resource
import com.ravikant.weather.weather_condition.domain.model.WeatherInfo
import kotlinx.coroutines.flow.Flow

interface WeatherInfoRepository {
    fun getWeatherInfo(city: String): Flow<Resource<List<WeatherInfo>>>
}