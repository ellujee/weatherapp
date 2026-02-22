package com.example.weather.ui.theme.weather

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weather.data.model.WeatherEntity
import com.example.weather.data.model.WeatherResponse
import com.example.weather.repository.WeatherRepository
import com.example.weather.utils.Result
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class WeatherViewModel(
    private val repository: WeatherRepository
) : ViewModel() {

    private val _weatherState = MutableStateFlow<Result<WeatherResponse>>(Result.Loading)
    val weatherState: StateFlow<Result<WeatherResponse>> = _weatherState.asStateFlow()

    private val _favoriteWeathers = MutableStateFlow<List<WeatherEntity>>(emptyList())
    val favoriteWeathers: StateFlow<List<WeatherEntity>> = _favoriteWeathers.asStateFlow()

    private val _searchQuery = MutableStateFlow("Helsinki")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    init {
        searchWeather()
        observeFavorites()
    }

    private fun observeFavorites() {
        viewModelScope.launch {
            repository.allWeather.collect { list ->
                _favoriteWeathers.value = list
            }
        }
    }

    fun onSearchQueryChange(query: String) {
        _searchQuery.value = query
    }

    fun searchWeather() {
        val city = _searchQuery.value
        if (city.isBlank()) return

        viewModelScope.launch {
            _weatherState.value = Result.Loading
            _weatherState.value = repository.getWeather(city)
        }
    }

    fun saveToFavorites(weather: WeatherResponse) {
        viewModelScope.launch {
            val entity = WeatherEntity(
                cityName = weather.name,
                temp = weather.main.temp,
                description = weather.weather.firstOrNull()?.description ?: "",
                humidity = weather.main.humidity,
                windSpeed = weather.wind.speed,
                icon = weather.weather.firstOrNull()?.icon ?: ""
            )
            repository.insert(entity)
        }
    }
}