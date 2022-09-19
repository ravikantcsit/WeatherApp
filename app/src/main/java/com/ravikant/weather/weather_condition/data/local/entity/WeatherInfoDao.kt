package com.ravikant.weather.weather_condition.data.local.entity

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface WeatherInfoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWeatherInfos(infos: List<WeatherInfoEntity>)

    @Query("DELETE FROM weatherinfoentity WHERE locationName IN(:cities)")
    suspend fun deleteWeatherInfos(cities: List<String>)

    @Query("SELECT * FROM weatherinfoentity WHERE locationName LIKE '%'||:city||'%' ")
    suspend fun getWeatherInfos(city: String): List<WeatherInfoEntity>

    @Query("SELECT * FROM weatherinfoentity")
    suspend fun getAllWeatherInfos(): List<WeatherInfoEntity>
}