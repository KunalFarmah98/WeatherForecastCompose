package com.kunalfarmah.apps.weatherforcastcompose.room.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.kunalfarmah.apps.weatherforcastcompose.room.dao.WeatherDao
import com.kunalfarmah.apps.weatherforcastcompose.room.entity.Favorite

@Database(entities = [Favorite::class], version = 1, exportSchema = false)
abstract class WeatherDatabase: RoomDatabase() {
    abstract fun weatherDao(): WeatherDao
}