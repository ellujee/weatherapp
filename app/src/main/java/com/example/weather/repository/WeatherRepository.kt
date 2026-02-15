package com.example.weather.repository

import com.example.weather.BuildConfig
import com.example.weather.data.model.WeatherResponse
import com.example.weather.data.remote.RetrofitClient
import com.example.weather.utils.Result
import retrofit2.HttpException
import java.io.IOException


class WeatherRepository {

    private val apiService = RetrofitClient.weatherApiService

    private val apiKey = "414a193d7a34b0d2e5280f52b59b7ff4"

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