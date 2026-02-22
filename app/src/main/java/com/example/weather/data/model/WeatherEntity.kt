package com.example.weather.data.model
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weather_cache")
data class WeatherEntity(
    @PrimaryKey val cityName: String,
    val temp: Double,
    val description: String,
    val humidity: Int,
    val windSpeed: Double,
    val icon: String,
    val timestamp: Long = System.currentTimeMillis()
)

