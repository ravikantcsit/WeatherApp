package com.ravikant.weather.weather_condition.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ravikant.weather.weather_condition.domain.model.WeatherInfo

@Composable
fun WeatherInfoItem(
    weatherInfo: WeatherInfo,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = weatherInfo.locationName,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        Text(text = weatherInfo.conditionText, fontWeight = FontWeight.Light)
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "wind:${weatherInfo.wind_kph} kph," +
                    "Temperature:${weatherInfo.temp_f} F", fontWeight = FontWeight.Light
        )
    }
}
