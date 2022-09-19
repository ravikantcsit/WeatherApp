package com.ravikant.weather.weather_condition.ui

import androidx.compose.runtime.MutableState
import kotlinx.coroutines.flow.SharedFlow

interface WeatherInfoViewModel {
    sealed class UIEvent {
        data class ShowSnackBar(val message: String) : UIEvent()
    }

    val weatherInfostate: MutableState<WeatherInfoState>

    val eventFlow: SharedFlow<UIEvent>

    fun searchQuery(query:String)

}