package com.ravikant.weather.weather_condition.data.remote

import com.ravikant.weather.weather_condition.data.remote.models.WeatherDto
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherAPI {

    @GET("/v1/current.json")
    suspend fun getWeatherInfo(
        @Query("key") api_key: String,
        @Query("q") word: String
    ): WeatherDto

    companion object {
        const val BASE_URL = "https://api.weatherapi.com"
        const val KEY = "520916eb3f46442ca1c12926221402"
    }
}