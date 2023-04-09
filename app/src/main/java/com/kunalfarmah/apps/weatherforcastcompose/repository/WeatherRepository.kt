package com.kunalfarmah.apps.weatherforcastcompose.repository

import com.kunalfarmah.apps.weatherforcastcompose.data.DataOrException
import com.kunalfarmah.apps.weatherforcastcompose.model.CurrentWeather
import com.kunalfarmah.apps.weatherforcastcompose.model.DailyForecast
import com.kunalfarmah.apps.weatherforcastcompose.network.WeatherApi
import javax.inject.Inject

class WeatherRepository @Inject constructor(private val weatherApi: WeatherApi) {

    suspend fun getDailyForecast(city: String) : DataOrException<DailyForecast, Boolean, Exception> {
        val response = try{
            weatherApi.getDailyForecast(city)
        }
        catch (e:Exception){
            return DataOrException(e=e)
        }

        return DataOrException(data = response)
    }


    suspend fun getCurrentWeather(city: String) : DataOrException<CurrentWeather, Boolean, Exception> {
        val response = try{
            weatherApi.getCurrentWeather(city)
        }
        catch (e:Exception){
            return DataOrException(e=e)
        }

        return DataOrException(data = response)
    }

}