package com.ravikant.weather.weather_condition.domain.usecases

import com.ravikant.weather.core.util.Resource
import com.ravikant.weather.weather_condition.domain.model.WeatherInfo
import com.ravikant.weather.weather_condition.domain.repository.WeatherInfoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetWeatherInfo(
    private val repository: WeatherInfoRepository
) {

    operator fun invoke(city: String): Flow<Resource<List<WeatherInfo>>> {
        if (city.isBlank())
            return flow { }
        return repository.getWeatherInfo(city = city)
    }
}