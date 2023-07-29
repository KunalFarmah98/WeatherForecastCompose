package com.kunalfarmah.apps.weatherforcastcompose.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.kunalfarmah.apps.weatherforcastcompose.room.entity.Favorite
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherDao {
    @Query("SELECT * FROM FavoritesTable")
    fun getFavorites(): Flow<List<Favorite>>

    @Query("SELECT * FROM FavoritesTable WHERE city = :city")
    suspend fun getFavoritesById(city: String):Favorite?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(favorite: Favorite)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateFavorite(favorite: Favorite)

    @Query("DELETE FROM FavoritesTable")
    suspend fun deleteAllFavorites()

    @Delete
    suspend fun deleteFavorite(favorite: Favorite)
}