package com.kunalfarmah.apps.weatherforcastcompose.screens.dropdown

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.kunalfarmah.apps.weatherforcastcompose.viewmodel.SettingsViewModel
import com.kunalfarmah.apps.weatherforcastcompose.widgets.WeatherAppBar
import kotlinx.coroutines.flow.map

@SuppressLint("FlowOperatorInvokedInComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    navController: NavController,
    settingsViewModel: SettingsViewModel = hiltViewModel()
) {

    val unitToggledState = remember {
        mutableStateOf(false)
    }

    val savedUnit = settingsViewModel.getSavedUnitState().collectAsState(initial = "Metric (°C)")

    var choiceState = remember {
        mutableStateOf(savedUnit.value)
    }
    Log.d("DATASTORE", "SettingsScreen: choiceState = ${choiceState.value}")
    Scaffold(topBar = {
        WeatherAppBar(
            navController = navController, title = "Settings",
            isMainScreen = false, icon = Icons.Default.ArrowBack
        ) {
            navController.popBackStack()
        }
    }) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(it)
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Change Units of Measurement",
                    modifier = Modifier.padding(bottom = 15.dp)
                )

                IconToggleButton(
                    checked = !unitToggledState.value, onCheckedChange = { state ->
                        unitToggledState.value = !unitToggledState.value
                        ///if (state) {
                            if (unitToggledState.value) {
                                choiceState.value = "Imperial (°F)"
                            } else {
                                choiceState.value = "Metric (°C)"
                            }
                       // }
                    }, modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .clip(shape = RectangleShape)
                        .padding(5.dp)
                        .background(Color.Magenta.copy(alpha = 0.4f))
                ) {
                    Text(
                        text = choiceState.value,
//                        text = if (unitToggledState.value) "Fahrenheit °F" else "Celcius °C",
                        color = Color.Black
                    )
                }

                Button(
                    onClick = {
                        settingsViewModel.saveUnit(choiceState.value)
                    },
                    modifier = Modifier
                        .padding(3.dp)
                        .align(CenterHorizontally),
                    shape = RoundedCornerShape(34.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0XFFEFBE42),
                        contentColor = Color.White
                    )

                ) {
                    Text(text = "Save", fontSize = 17.sp)
                }
            }
        }
    }

}