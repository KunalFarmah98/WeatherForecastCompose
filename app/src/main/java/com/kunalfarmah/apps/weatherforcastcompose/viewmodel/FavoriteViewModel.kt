package com.kunalfarmah.apps.weatherforcastcompose.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kunalfarmah.apps.weatherforcastcompose.repository.WeatherRepository
import com.kunalfarmah.apps.weatherforcastcompose.room.entity.Favorite
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(private val weatherRepository: WeatherRepository): ViewModel(){

    private val TAG = "FavoriteViewModel"
    private val _favList = MutableStateFlow<List<Favorite>>(emptyList())
        val favList = _favList.asStateFlow()

    init {
        viewModelScope.launch {
           weatherRepository.getFavorites().distinctUntilChanged().collect {
               Log.d(TAG, "favorites found: ${it.size}")
               _favList.value = it
               Log.d(TAG, "added favorites ${_favList.value}")
           }
        }
    }

    fun insertFavorite(favorite: Favorite) = viewModelScope.launch { weatherRepository.insertFavorite(favorite) }

    fun deleteFavorite(favorite: Favorite) = viewModelScope.launch { weatherRepository.deleteFavorite(favorite) }

}