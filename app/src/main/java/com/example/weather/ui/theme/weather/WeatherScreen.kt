package com.example.weather.ui.theme.weather

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.weather.utils.Result
import coil.compose.AsyncImage

@Composable
fun WeatherScreen(viewModel: WeatherViewModel) {
    val searchQuery by viewModel.searchQuery.collectAsState()
    val weatherState by viewModel.weatherState.collectAsState()
    val favorites by viewModel.favoriteWeathers.collectAsState()

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

                Button(
                    onClick = { viewModel.saveToFavorites(data) },
                    modifier = Modifier.padding(vertical = 8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
                ) {
                    Text("Tallenna")
                }

                Spacer(modifier = Modifier.height(8.dp))

                Card(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        WeatherDetailRow("Tuntuu kuin", "${data.main.feels_like.toInt()}°C")
                        WeatherDetailRow("Kosteus", "${data.main.humidity}%")
                        WeatherDetailRow("Tuuli", "${data.wind.speed} m/s")
                    }
                }
            }
            is Result.Error -> Text("Virhe: ${state.exception.message}", color = MaterialTheme.colorScheme.error)
        }

        Spacer(modifier = Modifier.height(24.dp))
        HorizontalDivider()
        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Tallennetut",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.align(Alignment.Start)
        )

        LazyColumn(
            modifier = Modifier.fillMaxWidth().weight(1f),
            contentPadding = PaddingValues(vertical = 8.dp)
        ) {
            items(favorites) { savedWeather ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(text = savedWeather.cityName, fontWeight = FontWeight.Bold)
                            Text(text = savedWeather.description, style = MaterialTheme.typography.bodySmall)
                        }
                        Text(
                            text = "${savedWeather.temp.toInt()}°C",
                            style = MaterialTheme.typography.titleLarge
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun WeatherDetailRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label)
        Text(text = value, fontWeight = FontWeight.Bold)
    }
}