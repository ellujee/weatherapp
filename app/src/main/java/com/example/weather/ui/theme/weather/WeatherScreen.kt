package com.example.weather.ui.theme.weather

import com.example.weather.ui.theme.weather.WeatherViewModel
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.weather.utils.Result
import coil.compose.AsyncImage

@Composable
fun WeatherScreen(viewModel: WeatherViewModel = viewModel()) {
    val searchQuery by viewModel.searchQuery.collectAsState()
    val weatherState by viewModel.weatherState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        OutlinedTextField(
            value = searchQuery,
            onValueChange = { viewModel.onSearchQueryChange(it) },
            label = { Text("Syötä kaupunki") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Button(
            onClick = { viewModel.searchWeather() },
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
        ) {
            Text("Hae")
        }

        when (val state = weatherState) {
            is Result.Loading -> CircularProgressIndicator()
            is Result.Success -> {
                val data = state.data

                Text(
                    text = "${data.name}, ${data.sys.country}",
                    style = MaterialTheme.typography.headlineMedium
                )

                val iconUrl = "https://openweathermap.org/img/wn/${data.weather[0].icon}@4x.png"
                AsyncImage(
                    model = iconUrl,
                    contentDescription = null,
                    modifier = Modifier.size(100.dp)
                )

                Text(
                    text = "${data.main.temp.toInt()}°C",
                    style = MaterialTheme.typography.displayLarge
                )

                Text(
                    text = data.weather[0].description.replaceFirstChar { it.uppercase() },
                    style = MaterialTheme.typography.titleLarge
                )

                Spacer(modifier = Modifier.height(16.dp))

                Card(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        WeatherDetailRow("Tuntuu kuin", "${data.main.feels_like.toInt()}°C")
                        WeatherDetailRow("Min / Max", "${data.main.tempMin.toInt()}°C / ${data.main.tempMax.toInt()}°C")
                        WeatherDetailRow("Kosteus", "${data.main.humidity}%")
                        WeatherDetailRow("Tuuli", "${data.wind.speed} m/s")
                        WeatherDetailRow("Paine", "${data.main.pressure} hPa")
                    }
                }
            }
            is Result.Error -> Text("Virhe: ${state.exception.message}")
        }
    }
}


@Composable
fun WeatherDetailRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label)
        Text(text = value, style = MaterialTheme.typography.bodyLarge)
    }
}