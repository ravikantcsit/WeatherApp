package com.ravikant.weather.weather_condition.ui

import com.ravikant.weather.weather_condition.domain.model.WeatherInfo

data class WeatherInfoState(
    val weatherInfoItems:List<WeatherInfo> = emptyList(),
    val loading: Boolean=false
)