package com.example.weather.data.model
import com.google.gson.annotations.SerializedName

data class Main(
    val temp: Double,
    val feels_like: Double,
    @SerializedName("temp_min") val tempMin: Double,
    @SerializedName("temp_max") val tempMax: Double,
    val pressure: Int,
    val humidity: Int
)
