package com.example.weather

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.weather.data.local.AppDatabase
import com.example.weather.repository.WeatherRepository
import com.example.weather.ui.theme.WeatherTheme
import com.example.weather.ui.theme.weather.WeatherScreen
import com.example.weather.ui.theme.weather.WeatherViewModel
import com.example.weather.ui.theme.weather.WeatherViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val database by lazy { AppDatabase.getDatabase(this) }

        val repository by lazy { WeatherRepository(database.weatherDao()) }

        val viewModel: WeatherViewModel by viewModels {
            WeatherViewModelFactory(repository)
        }

        setContent {
            WeatherTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    WeatherScreen(viewModel = viewModel)
                }
            }
        }
    }
}
