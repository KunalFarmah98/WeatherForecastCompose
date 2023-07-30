package com.kunalfarmah.apps.weatherforcastcompose.screens.home

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.kunalfarmah.apps.weatherforcastcompose.data.DataOrException
import com.kunalfarmah.apps.weatherforcastcompose.model.DailyForecast
import com.kunalfarmah.apps.weatherforcastcompose.nav.WeatherScreens
import com.kunalfarmah.apps.weatherforcastcompose.viewmodel.SettingsViewModel
import com.kunalfarmah.apps.weatherforcastcompose.viewmodel.WeatherViewModel
import com.kunalfarmah.apps.weatherforcastcompose.widgets.HumidityWindPressureRow
import com.kunalfarmah.apps.weatherforcastcompose.widgets.SunsetSunriseRow
import com.kunalfarmah.apps.weatherforcastcompose.widgets.WeatherAppBar
import com.kunalfarmah.apps.weatherforcastcompose.widgets.WeatherDetailList
import com.kunalfarmah.apps.weatherforcastcompose.widgets.WeatherStateImage
import java.text.SimpleDateFormat
import java.util.Date
import kotlin.math.roundToInt


@Composable
fun HomeScreen(navController: NavController, city: String?) {
    val weatherViewModel: WeatherViewModel = hiltViewModel()
    val settingsViewModel: SettingsViewModel = hiltViewModel()

    val unit = remember {
        mutableStateOf("")
    }
    fun stateCallback(it: String){
        if(it.isNullOrEmpty()){
            unit.value = "metric"
        }
        if(unit.value == ""){
            when(it){
                "Metric (°C)" -> unit.value = "metric"
                "Imperial (°F)" -> unit.value = "imperial"
            }
        }
    }
    settingsViewModel.getSavedUnitState(::stateCallback)
//    weatherViewModel.getCurrentForecast(city?:"delhi")
    weatherViewModel.getDailyForecast(city ?: "delhi", unit.value)
//    val currentData = weatherViewModel.currentData.collectAsState()
    val dailyData = weatherViewModel.dailyData.collectAsState().value
    if (dailyData.data == null || dailyData.loading == true)
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            CircularProgressIndicator()
        }
    else
        MainScaffold(dailyData.data, navController, unit.value)
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScaffold(
    dailyData: DailyForecast?,
    navController: NavController,
    unit: String
) {
    Scaffold(topBar = {
        WeatherAppBar(
            title = "${dailyData?.city?.name ?: ""}, ${dailyData?.city?.country ?: ""}",
            navController = navController,
            elevation = 5.dp,
            onAddActionClicked = {
                navController.navigate(WeatherScreens.SearchScreen.name)
            }
        ) {
            Log.d("Home", "MainScaffold: button clicked")
        }
    }) {
        MainContent(dailyData, paddingValues = it, unit)
    }
}

@Composable
fun MainContent(
    dailyData: DailyForecast?,
    paddingValues: PaddingValues,
    unit: String
) {
    val imageUrl =
        "https://openweathermap.org/img/wn/${dailyData?.list?.get(0)?.weather?.get(0)?.icon}.png"
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                top = paddingValues.calculateTopPadding(),
                start = 4.dp,
                end = 4.dp,
                bottom = paddingValues.calculateBottomPadding()
            ),
        verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = SimpleDateFormat("EEE, dd/MM/yyyy").format(
                Date(
                    (dailyData?.list?.get(0)?.dt?.toLong() ?: 0) * 1000
                )
            ).toString(),
            style = MaterialTheme.typography.labelLarge,
            color = Color.Black,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(6.dp)
        )

        Surface(
            modifier = Modifier
                .padding(4.dp)
                .size(200.dp),
            shape = CircleShape,
            color = Color(0xFFFFC400)
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                WeatherStateImage(imageUrl)
                Text(
                    text = dailyData?.list?.get(0)?.temp?.day?.roundToInt().toString()
                        .plus(if(unit == "metric") "°C" else "°F"),
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.ExtraBold
                )
                Text(
                    text = dailyData?.list?.get(0)?.weather?.get(0)?.main ?: "",
                    fontStyle = FontStyle.Italic
                )
            }
        }

        HumidityWindPressureRow(weather = dailyData?.list?.get(0), unit)
        Divider()
        SunsetSunriseRow(weather = dailyData?.list?.get(0))

        Text(
            text = "This Week",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )
        WeatherDetailList(data = dailyData, unit)

    }
}


