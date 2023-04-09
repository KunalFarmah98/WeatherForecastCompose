package com.kunalfarmah.apps.weatherforcastcompose.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kunalfarmah.apps.weatherforcastcompose.data.DataOrException
import com.kunalfarmah.apps.weatherforcastcompose.model.CurrentWeather
import com.kunalfarmah.apps.weatherforcastcompose.model.DailyForecast
import com.kunalfarmah.apps.weatherforcastcompose.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(private val weatherRepository: WeatherRepository) :
    ViewModel() {

    private val dailyDataFlow : MutableStateFlow<DataOrException<DailyForecast, Boolean, Exception>> = MutableStateFlow(
    DataOrException(null, true, null)
    )
    val dailyData: StateFlow<DataOrException<DailyForecast, Boolean, Exception>> = dailyDataFlow


    private val currentDataFlow: MutableStateFlow<DataOrException<CurrentWeather, Boolean, Exception>> =
        MutableStateFlow(DataOrException(null, true, null))
    val currentData: StateFlow<DataOrException<CurrentWeather, Boolean, Exception>> = currentDataFlow

    fun getDailyForecast(city: String) {
        viewModelScope.launch {
            if (city.isNullOrEmpty())
                return@launch
            dailyData.value.loading = true
            val response = weatherRepository.getDailyForecast(city)
            dailyDataFlow.value = DataOrException(data = response.data, loading = false, e = response.e)
        }
    }

    fun getCurrentForecast(city: String) {
        viewModelScope.launch {
            if (city.isNullOrEmpty())
                return@launch
            currentDataFlow.value.loading = true
            val response = weatherRepository.getCurrentWeather(city)
            currentDataFlow.value = DataOrException(data = response.data, loading = false, e = response.e)
        }
    }

}