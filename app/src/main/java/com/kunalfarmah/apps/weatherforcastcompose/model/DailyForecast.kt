package com.kunalfarmah.apps.weatherforcastcompose.model

data class DailyForecast(
    val city: City,
    val cnt: Int, // 7
    val cod: String, // 200
    val list: List<WeatherList>,
    val message: Double // 4.3794327
)

data class City(
    val coord: Coord,
    val country: String, // IN
    val id: Int, // 1273294
    val name: String, // Delhi
    val population: Int, // 10927986
    val timezone: Int // 19800
)


data class WeatherList(
    val clouds: Int, // 0
    val deg: Int, // 286
    val dt: Int, // 1681021800
    val feels_like: FeelsLike,
    val gust: Double, // 6.1
    val humidity: Int, // 10
    val pop: Double, // 0.03
    val pressure: Int, // 1015
    val speed: Double, // 5.02
    val sunrise: Int, // 1681000355
    val sunset: Int, // 1681045964
    val temp: Temp,
    val weather: List<Weather>
)

data class FeelsLike(
    val day: Double, // 30.9
    val eve: Double, // 32.31
    val morn: Double, // 23.26
    val night: Double // 27.26
)

data class Temp(
    val day: Double, // 33.35
    val eve: Double, // 35.01
    val max: Double, // 35.32
    val min: Double, // 24.22
    val morn: Double, // 24.3
    val night: Double // 28.87
)



