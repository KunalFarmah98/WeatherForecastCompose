package com.kunalfarmah.apps.weatherforcastcompose.screens.home

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import com.kunalfarmah.apps.weatherforcastcompose.R
import com.kunalfarmah.apps.weatherforcastcompose.data.DataOrException
import com.kunalfarmah.apps.weatherforcastcompose.model.DailyForecast
import com.kunalfarmah.apps.weatherforcastcompose.model.Weather
import com.kunalfarmah.apps.weatherforcastcompose.model.WeatherList
import com.kunalfarmah.apps.weatherforcastcompose.viewmodel.WeatherViewModel
import com.kunalfarmah.apps.weatherforcastcompose.widgets.WeatherAppBar
import java.text.SimpleDateFormat
import java.util.Date
import kotlin.math.roundToInt


@Composable
fun HomeScreen(navController: NavController) {
    val weatherViewModel: WeatherViewModel = hiltViewModel()
    weatherViewModel.getCurrentForecast("delhi")
    weatherViewModel.getDailyForecast("delhi")
    val currentData = weatherViewModel.currentData.collectAsState()
    val dailyData = weatherViewModel.dailyData.collectAsState().value
    MainScaffold(dailyData, navController)
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScaffold(dailyData: DataOrException<DailyForecast, Boolean, Exception>, navController: NavController) {
    Scaffold(topBar = {
        WeatherAppBar(
            title = "${dailyData.data?.city?.name ?: ""}, ${dailyData.data?.city?.country ?: ""}",
            navController = navController,
            elevation = 5.dp
        ) {
            Log.d("Home", "MainScaffold: button clicked")
        }
    }) {
        MainContent(dailyData, paddingValues = it)
    }
}

@Composable
fun MainContent(dailyData:DataOrException<DailyForecast, Boolean, Exception>, paddingValues: PaddingValues) {
    val imageUrl = "https://openweathermap.org/img/wn/${dailyData.data?.list?.get(0)?.weather?.get(0)?.icon}.png"
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
            text = SimpleDateFormat("EEE, dd/MM/yyyy").format(Date((dailyData.data?.list?.get(0)?.dt?.toLong() ?: 0)*1000)).toString(),
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
                Text(text = dailyData.data?.list?.get(0)?.temp?.day?.roundToInt().toString().plus(" \u2103"), style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.ExtraBold)
                Text(text = dailyData.data?.list?.get(0)?.weather?.get(0)?.main ?:"", fontStyle = FontStyle.Italic)
            }
        }
        
        HumidityWindPressureRow(weather = dailyData.data?.list?.get(0))
        Divider()
        SunsetSunriseRow(weather = dailyData.data?.list?.get(0))
        
        Text(text = "This Week", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
        WeatherDetailList(data = dailyData.data)

    }
}

@Composable
fun WeatherStateImage(imageUrl: String) {
    Image(painter = rememberAsyncImagePainter(imageUrl), contentDescription = "icon image",
    modifier = Modifier.size(80.dp), alpha = 1f, contentScale = ContentScale.Fit)
}

@Composable
fun HumidityWindPressureRow(weather: WeatherList?){
    Row(modifier = Modifier
        .padding(12.dp)
        .fillMaxWidth(),
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.SpaceBetween) {
        Row(modifier = Modifier.padding(4.dp)){
            Icon(painter = painterResource(id = R.drawable.humidity), contentDescription = "humidity icon",
            modifier = Modifier.size(20.dp))
            Text(text = "${weather?.humidity}%", style = MaterialTheme.typography.bodyMedium)
        }

        Row(modifier = Modifier.padding(4.dp)){
            Icon(painter = painterResource(id = R.drawable.pressure), contentDescription = "pressure icon",
                modifier = Modifier.size(20.dp))
            Text(text = "${weather?.pressure} psi", style = MaterialTheme.typography.bodyMedium)
        }

        Row(modifier = Modifier.padding(4.dp)){
            Icon(painter = painterResource(id = R.drawable.wind), contentDescription = "wind icon",
                modifier = Modifier.size(20.dp))
            Text(text = "${weather?.speed} km/h", style = MaterialTheme.typography.bodyMedium)
        }
    }
}

@Composable
fun SunsetSunriseRow(weather: WeatherList?){
    Row(modifier = Modifier
        .padding(12.dp)
        .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween) {
        Row(modifier = Modifier.padding(4.dp)){
            Icon(painter = painterResource(id = R.drawable.sunrise), contentDescription = "sunrise icon",
                modifier = Modifier.size(20.dp))
            Text(text = "${SimpleDateFormat("hh:mm aa").format(Date(weather?.sunrise?.toLong() ?: 0))}", style = MaterialTheme.typography.bodyMedium)
        }

        Row(modifier = Modifier.padding(4.dp)){
            Icon(painter = painterResource(id = R.drawable.sunset), contentDescription = "sunset icon",
                modifier = Modifier.size(20.dp))
            Text(text = "${SimpleDateFormat("hh:mm aa").format(Date(weather?.sunset?.toLong() ?: 0))}", style = MaterialTheme.typography.bodyMedium)
        }
        
    }
}

@Composable
fun WeatherItem(weather: WeatherList){
    val imageUrl = "https://openweathermap.org/img/wn/${weather.weather[0].icon}.png"
    Surface(modifier = Modifier
        .padding(3.dp)
        .fillMaxWidth(),
        color = Color.White,
        shape = CircleShape.copy(topStart = CornerSize(0.dp), topEnd = CornerSize(0.dp))
    ) {
        Row(modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween){
            Text(text = SimpleDateFormat("EEE").format(Date((weather.dt?.toLong() ?: 0)*1000)).toString(),
                modifier = Modifier.padding(start = 5.dp))
            WeatherStateImage(imageUrl = imageUrl)
            Surface(
                modifier=  Modifier.padding(0.dp),
                shape = CircleShape,
                color = Color(0xFFFFC400)){
                Text(text = weather.weather[0].description,
                    modifier = Modifier.padding(4.dp),
                    style = MaterialTheme.typography.bodyMedium)
            }
            Text(text = buildAnnotatedString {
                withStyle(style = SpanStyle(
                    color = Color.Blue.copy(alpha = 0.7f),
                    fontWeight = FontWeight.SemiBold
                )){
                    append(weather.temp.max.roundToInt().toString().plus("\u2103"))
                }
                append("/")
                withStyle(style = SpanStyle(
                    color = Color.LightGray,
                    fontWeight = FontWeight.SemiBold
                )){
                    append(weather.temp.min.roundToInt().toString().plus("\u2103"))
                }
        })

        }
    }
}

@Composable
fun WeatherDetailList(data: DailyForecast?){
    Surface(modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight()
        .padding(4.dp)
    ,
        color = Color(0xFFEEF1EF),
        shape = RoundedCornerShape(14.dp)
    ) {

        LazyColumn(modifier = Modifier.padding(2.dp),
        contentPadding = PaddingValues(2.dp)
        ){
            items(data?.list?.minus(data.list.first())?: listOf()){ item ->
                WeatherItem(weather = item)
            }
        }

    }
}
