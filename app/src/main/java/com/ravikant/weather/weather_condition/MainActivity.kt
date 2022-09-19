package com.ravikant.weather.weather_condition

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import com.ravikant.weather.weather_condition.ui.OnLifecycleEvent
import com.ravikant.weather.weather_condition.ui.WeatherInfoItem
import com.ravikant.weather.weather_condition.ui.WeatherInfoViewModel
import com.ravikant.weather.weather_condition.ui.WeatherInfoViewModelImpl
import com.ravikant.weather.weather_condition.ui.theme.WeatherAppTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var weatherInfoViewModel: WeatherInfoViewModelImpl

    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WeatherAppTheme {
                weatherInfoViewModel = hiltViewModel()

                val state = weatherInfoViewModel.weatherInfostate.value
                val scaffoldState = rememberScaffoldState()

                LaunchedEffect(key1 = true) {
                    weatherInfoViewModel.eventFlow.collectLatest { event ->
                        when (event) {
                            is WeatherInfoViewModel.UIEvent.ShowSnackBar -> {
                                scaffoldState.snackbarHostState.showSnackbar(
                                    message = event.message
                                )
                            }
                        }

                    }
                }

                Scaffold(
                    scaffoldState = scaffoldState
                ) {
                    Box(modifier = Modifier.background(MaterialTheme.colors.background)) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(16.dp)
                        ) {
                            LazyColumn(modifier = Modifier.fillMaxSize()) {
                                items(state.weatherInfoItems.size) { i ->
                                    val weatherInfo = state.weatherInfoItems[i]
                                    if (i > 0) {
                                        Spacer(modifier = Modifier.height(8.dp))
                                    }
                                    WeatherInfoItem(weatherInfo = weatherInfo)
                                    if (i < state.weatherInfoItems.size - 1) {
                                        Divider()
                                    }
                                }
                            }

                            OnLifecycleEvent { owner, event ->
                                // do stuff on event
                                when (event) {
                                    Lifecycle.Event.ON_RESUME -> {
                                        if (::weatherInfoViewModel.isInitialized) {
                                            weatherInfoViewModel.searchQuery("Bhilai")
                                        }
                                    }
                                    else -> {

                                    }
                                }
                            }

                        }
                    }

                }
            }
        }
    }

}