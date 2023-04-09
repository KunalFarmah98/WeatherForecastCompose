package com.kunalfarmah.apps.weatherforcastcompose.network

import com.kunalfarmah.apps.weatherforcastcompose.model.CurrentWeather
import com.kunalfarmah.apps.weatherforcastcompose.model.DailyForecast
import com.kunalfarmah.apps.weatherforcastcompose.util.Constants.API_KEY_FREE
import com.kunalfarmah.apps.weatherforcastcompose.util.Constants.API_KEY_PRO
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {
    @GET("/data/2.5/weather")
    suspend fun getCurrentWeather(
        @Query("q") city: String, @Query("appId") apiKey: String = API_KEY_FREE,
        @Query("units") units: String = "metric"
    ): CurrentWeather

    @GET("/data/2.5/forecast/daily")
    suspend fun getDailyForecast(
        @Query("q") city: String, @Query("appId") apiKey: String = API_KEY_PRO,
        @Query("units") units: String = "metric"
    ): DailyForecast
}