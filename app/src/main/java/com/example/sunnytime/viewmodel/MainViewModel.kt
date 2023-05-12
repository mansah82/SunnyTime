package com.example.sunnytime.viewmodel

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sunnytime.api.city.AutocompleteService
import com.example.sunnytime.api.city.PlaceIdService
import com.example.sunnytime.api.weather.WeatherService
import com.example.sunnytime.viewmodel.model.State
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*


class MainViewModel : ViewModel() {


    private val _state = MutableStateFlow(State())
    private val weatherService = WeatherService.create()
    private val autoCompleteService = AutocompleteService.create()
    private val placeIdService = PlaceIdService.create()
    val state: StateFlow<State> = _state
    private val currentState = _state.value

    fun onSearch(searchText: String) {
        viewModelScope.launch {
            try {
                val autocompleteResponse = withContext(Dispatchers.Unconfined) {
                    autoCompleteService.getCities(searchText = searchText)
                }
                if (autocompleteResponse != null) {
                    _state.value = currentState.copy(
                        listOfCities = autocompleteResponse.predictions
                    )
                }
            } catch (e: Exception) {
                println("Error ${e.message}")
            }
        }
    }

    @SuppressLint("SimpleDateFormat")
    fun getDate(): String {

        val simpleDate = SimpleDateFormat("yyyy-MM-dd")

        return simpleDate.format(Date())
    }

    fun onCityClicked(placeId: String) {
        viewModelScope.launch {
            try {
                val placeIdResponse = withContext(Dispatchers.Unconfined) {
                    placeIdService.getCoordinates(placeId = placeId)
                }
                if (placeIdResponse != null) {
                    getUVIndex(
                        lat = placeIdResponse.results[0].geometry.location.lat,
                        lng = placeIdResponse.results[0].geometry.location.lng
                    )

                }
            } catch (e: Exception) {
                println("Error ${e.message}")
            }
        }

    }

    private fun getUVIndex(lat: Double, lng: Double) {
        viewModelScope.launch {
            try {
                val weatherResponse = withContext(Dispatchers.Unconfined) {
                    weatherService.getWeather(
                        lat = lat,
                        lng = lng,
                        dayToFetch = getDate()
                    )
                }
                _state.value = currentState.copy(
                    weather = weatherResponse,
                    uvIndex = weatherResponse?.daily?.uv_index_max?.get(0)
                )
            } catch (e: Exception) {
                println("Error ${e.message}")
            }
            println("UX ${state.value.uvIndex}")
        }
    }

    fun updateWeatherShowing(isShowing: Boolean) {

        _state.value = currentState.copy(isWeatherShowing = isShowing)
    }
}



