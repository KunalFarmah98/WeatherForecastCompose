package com.kunalfarmah.apps.weatherforcastcompose.viewmodel

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kunalfarmah.apps.weatherforcastcompose.repository.WeatherRepository
import com.kunalfarmah.apps.weatherforcastcompose.room.entity.Favorite
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(@ApplicationContext private val context: Context, private val weatherRepository: WeatherRepository): ViewModel(){

    private val TAG = "FavoriteViewModel"
    private val _favList = MutableStateFlow<List<Favorite>>(emptyList())
        val favList = _favList.asStateFlow()

    init {
        viewModelScope.launch {
           weatherRepository.getFavorites().distinctUntilChanged().collect {
               Log.d(TAG, "favorites found: ${it.size}")
               if(it.isNotEmpty()) {
                   _favList.value = it
                   Log.d(TAG, "added favorites ${_favList.value}")
               }
           }
        }
    }

    fun insertFavorite(favorite: Favorite) = viewModelScope.launch {
        weatherRepository.insertFavorite(favorite)
    }.invokeOnCompletion {
        if(it==null){
            CoroutineScope(Dispatchers.Main).launch {
                Toast.makeText(
                    context,
                    "Successfully added ${favorite.city} as favorite",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    fun deleteFavorite(favorite: Favorite) = viewModelScope.launch { weatherRepository.deleteFavorite(favorite) }.invokeOnCompletion {
        if(it==null){
            CoroutineScope(Dispatchers.Main).launch {
                Toast.makeText(
                    context,
                    "Successfully removed ${favorite.city} from favorites",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    suspend fun getFavoriteByCity(city: String) = weatherRepository.getFavoriteByCity(city)
}