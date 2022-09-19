package com.ravikant.weather.weather_condition.di

import android.app.Application
import androidx.room.Room
import com.ravikant.weather.weather_condition.data.local.entity.WeatherInfoDatabase
import com.ravikant.weather.weather_condition.data.remote.WeatherAPI
import com.ravikant.weather.weather_condition.data.repository.WeatherInfoRepositoryImpl
import com.ravikant.weather.weather_condition.domain.repository.WeatherInfoRepository
import com.ravikant.weather.weather_condition.domain.usecases.GetWeatherInfo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object WeatherInfoModule {

    @Provides
    @Singleton
    fun provideWeatherInfoUseCase(weatherInfoRepository: WeatherInfoRepository): GetWeatherInfo {
        return GetWeatherInfo(weatherInfoRepository)
    }

    @Provides
    @Singleton
    fun provideWeatherInfoRepository(
        api: WeatherAPI,
        db: WeatherInfoDatabase
    ): WeatherInfoRepository {
        return WeatherInfoRepositoryImpl(api, db.dao)
    }

    @Provides
    @Singleton
    fun provideWeatherInfoDatabase(application: Application): WeatherInfoDatabase {
        return Room.databaseBuilder(
            application,
            WeatherInfoDatabase::class.java, "weather_info_db"
        )
            .build()
    }

    @Provides
    @Singleton
    fun provideWeatherAPI(): WeatherAPI {
        return Retrofit.Builder()
            .baseUrl(WeatherAPI.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WeatherAPI::class.java)
    }
}