package com.example.weather.data.model



data class WeatherResponse(
    val weather: List<Weather>,
    val main: Main,
    val wind: Wind,
    val name: String,
    val sys: Sys)
