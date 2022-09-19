package com.ravikant.weather.weather_condition.data.repository

import com.ravikant.weather.core.util.Resource
import com.ravikant.weather.weather_condition.data.local.entity.WeatherInfoDao
import com.ravikant.weather.weather_condition.data.remote.WeatherAPI
import com.ravikant.weather.weather_condition.data.remote.models.WeatherDto
import com.ravikant.weather.weather_condition.domain.model.WeatherInfo
import com.ravikant.weather.weather_condition.domain.repository.WeatherInfoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class WeatherInfoRepositoryImpl(
    val api: WeatherAPI,
    val dao: WeatherInfoDao
) : WeatherInfoRepository {
    override fun getWeatherInfo(city: String): Flow<Resource<List<WeatherInfo>>> = flow {
        emit(Resource.Loading())

        val weatherInfos = dao.getWeatherInfos(city).map { it.toWeatherInfo() }
        emit(Resource.Loading(data = weatherInfos))

        try {
            val remoteWeatherInfos = mutableListOf<WeatherDto>()
            val remoteWeatherInfo = api.getWeatherInfo(WeatherAPI.KEY, city)
            remoteWeatherInfos.add(remoteWeatherInfo)
            dao.insertWeatherInfos(remoteWeatherInfos.map { it.toWeatherInfoEntity() })

        } catch (e: HttpException) {
            emit(Resource.Error("Sorry, couldn't reach server", data = weatherInfos))
        } catch (e: IOException) {
            emit(Resource.Error(message = "something went wrong", data = weatherInfos))
        }

        val updatedWeatherInfos = dao.getAllWeatherInfos().map { it.toWeatherInfo() }

        emit(Resource.Success(updatedWeatherInfos))
    }
}