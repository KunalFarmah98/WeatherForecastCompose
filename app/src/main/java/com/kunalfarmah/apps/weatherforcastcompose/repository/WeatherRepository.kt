package com.kunalfarmah.apps.weatherforcastcompose.repository

import com.kunalfarmah.apps.weatherforcastcompose.data.DataOrException
import com.kunalfarmah.apps.weatherforcastcompose.model.CurrentWeather
import com.kunalfarmah.apps.weatherforcastcompose.model.DailyForecast
import com.kunalfarmah.apps.weatherforcastcompose.network.WeatherApi
import com.kunalfarmah.apps.weatherforcastcompose.room.dao.WeatherDao
import com.kunalfarmah.apps.weatherforcastcompose.room.entity.Favorite
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class WeatherRepository @Inject constructor(private val weatherApi: WeatherApi, private val weatherDao: WeatherDao) {

    /**
     * Api Functions
     */
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


    /**
     * Database Functions
     */

    fun getFavorites(): Flow<List<Favorite>> = weatherDao.getFavorites()

    suspend fun insertFavorite(favorite: Favorite) = weatherDao.insertFavorite(favorite)

    suspend fun updateFavorite(favorite: Favorite) = weatherDao.updateFavorite(favorite)

    suspend fun deleteAllFavorites() = weatherDao.deleteAllFavorites()

    suspend fun deleteFavorite(favorite: Favorite) = weatherDao.deleteFavorite(favorite)

    suspend fun getFavoriteByCity(city:String) = weatherDao.getFavoritesById(city)

}