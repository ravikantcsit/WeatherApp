package com.ravikant.weather.weather_condition.ui

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ravikant.weather.core.util.Resource
import com.ravikant.weather.weather_condition.domain.usecases.GetWeatherInfo
import com.ravikant.weather.weather_condition.ui.WeatherInfoViewModel.UIEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherInfoViewModelImpl @Inject constructor(
    val getWeatherUseCase: GetWeatherInfo
) : WeatherInfoViewModel, ViewModel() {

    private val _searchQuery = mutableStateOf("")
    val searchQuery: State<String> = _searchQuery

    private val _weatherInfostate = mutableStateOf(WeatherInfoState())
    override val weatherInfostate: MutableState<WeatherInfoState> = _weatherInfostate

    init {
        searchQuery("Raipur")
    }

    private val _eventFlow = MutableSharedFlow<UIEvent>()
    override val eventFlow = _eventFlow.asSharedFlow()

    private var searchJob: Job? = null

    override fun searchQuery(query: String) {
        _searchQuery.value = query
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            //loading data for 5 cities
            for (i in 1..5) {
                getWeatherUseCase(query).onEach { result ->
                    when (result) {
                        is Resource.Success -> {
                            _weatherInfostate.value = weatherInfostate.value.copy(
                                weatherInfoItems = result.data ?: emptyList(),
                                loading = false
                            )
                        }
                        is Resource.Error -> {
                            _weatherInfostate.value = weatherInfostate.value.copy(
                                weatherInfoItems = result.data ?: emptyList(),
                                loading = false
                            )
                            _eventFlow.emit(
                                UIEvent.ShowSnackBar(
                                    result.message ?: "Unknown error"
                                )
                            )
                        }
                        is Resource.Loading -> {
                            _weatherInfostate.value = weatherInfostate.value.copy(
                                weatherInfoItems = result.data ?: emptyList(),
                                loading = true
                            )
                        }
                    }
                }.launchIn(this)
            }
        }
    }
}

