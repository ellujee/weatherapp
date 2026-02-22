package com.example.weather.repository

import com.example.weather.data.local.WeatherDao
import com.example.weather.data.model.WeatherEntity
import com.example.weather.data.model.WeatherResponse
import com.example.weather.data.remote.RetrofitClient
import com.example.weather.utils.Result
import kotlinx.coroutines.flow.Flow
import retrofit2.HttpException
import java.io.IOException

class WeatherRepository(private val weatherDao: WeatherDao) {

    private val apiService = RetrofitClient.weatherApiService
    private val apiKey = "414a193d7a34b0d2e5280f52b59b7ff4"

    val allWeather: Flow<List<WeatherEntity>> = weatherDao.getAllWeather()

    suspend fun insert(weather: WeatherEntity) {
        weatherDao.insertWeather(weather)
    }

    suspend fun getWeather(city: String): Result<WeatherResponse> {
        return try {
            val response = apiService.getWeather(city, apiKey)
            Result.Success(response)
        } catch (e: IOException) {
            Result.Error(Exception("Verkkovirhe: Tarkista nettiyhteys"))
        } catch (e: HttpException) {
            Result.Error(Exception("Virhe: ${e.code()} - Kaupunkia ei löytynyt"))
        } catch (e: Exception) {
            Result.Error(Exception("Tuntematon virhe: ${e.message}"))
        }
    }
}