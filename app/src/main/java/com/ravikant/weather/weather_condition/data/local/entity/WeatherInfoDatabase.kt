package com.ravikant.weather.weather_condition.data.local.entity

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [WeatherInfoEntity::class],
    version = 1
)
abstract class WeatherInfoDatabase : RoomDatabase() {
    abstract val dao: WeatherInfoDao
}