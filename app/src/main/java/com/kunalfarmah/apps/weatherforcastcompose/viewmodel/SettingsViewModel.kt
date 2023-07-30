package com.kunalfarmah.apps.weatherforcastcompose.viewmodel

import android.content.Context
import android.util.Log
import androidx.compose.runtime.collectAsState
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.log

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = "settings"
)
@HiltViewModel
class SettingsViewModel @Inject constructor(@ApplicationContext val context: Context): ViewModel() {

    fun saveUnit(unit: String){
        Log.d("DATASTORE", "saveUnit: $unit")
        viewModelScope.launch {
            context.dataStore.edit {
                it[stringPreferencesKey("unit")] = unit
            }
        }
    }

    fun getSavedUnitState(callback: (unit:String)->Unit) {
        val unit = context.dataStore.data.map {
            val value = it[stringPreferencesKey("unit")] ?: "Metric °C"
            Log.d("DATASTORE", "getSavedUnitState: $value")
            value
        }
        viewModelScope.launch {
            unit.collectLatest {
                callback(it)
            }
        }
    }
}