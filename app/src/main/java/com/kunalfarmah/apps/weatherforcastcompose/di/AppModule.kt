package com.kunalfarmah.apps.weatherforcastcompose.di

import android.content.Context
import androidx.room.Room
import com.kunalfarmah.apps.weatherforcastcompose.network.WeatherApi
import com.kunalfarmah.apps.weatherforcastcompose.room.dao.WeatherDao
import com.kunalfarmah.apps.weatherforcastcompose.room.database.WeatherDatabase
import com.kunalfarmah.apps.weatherforcastcompose.util.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    @Singleton
    fun provideApi(): WeatherApi {
        return Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(WeatherApi::class.java)
    }

    @Singleton
    @Provides
    fun provideWeatherDao(weatherDatabase: WeatherDatabase): WeatherDao = weatherDatabase.weatherDao()

    @Singleton
    @Provides
    fun provideWeatherDataBase(@ApplicationContext applicationContext: Context): WeatherDatabase =
        Room.databaseBuilder(applicationContext, WeatherDatabase::class.java, "weather_database")
            .fallbackToDestructiveMigration().build()

}