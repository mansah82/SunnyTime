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
    private fun getCurrentState(): State = _state.value

    fun onSearch(searchText: String) {
        viewModelScope.launch {
            try {
                val autocompleteResponse = withContext(Dispatchers.Unconfined) {
                    autoCompleteService.getCities(searchText = searchText)
                }
                if (autocompleteResponse != null) {
                    _state.value = getCurrentState().copy(
                        listOfCities = autocompleteResponse.predictions
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
                updateWeatherShowing(isShowing = true)

                _state.value = getCurrentState().copy(
                    weather = weatherResponse,
                    uvIndex = weatherResponse?.daily?.uv_index_max?.get(0)
                )
            } catch (e: Exception) {
                println("Error ${e.message}")
            }
        }
    }

    fun updateDateOffset(offset: Int) {
        _state.value = getCurrentState().copy(
            dateOffset = offset
        )
        getUVIndex(
            lat = state.value.latitude,
            lng = state.value.longitude
        )
    }

    @SuppressLint("SimpleDateFormat")
    fun getDate(): String {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_MONTH, state.value.dateOffset)
        val simpleDate = SimpleDateFormat("yyyy-MM-dd")
        val dayOfWeek =
            calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault())


        val formattedDayOfTheWeek = when (state.value.dateOffset) {
            0 -> "Today"
            -1 -> "Yesterday"
            1 -> "Tomorrow"
            else -> dayOfWeek
        }
        _state.value = getCurrentState().copy(
            dayOfTheWeek = formattedDayOfTheWeek
        )

        return simpleDate.format(calendar.time)
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
                    _state.value = getCurrentState().copy(
                        chosenCity = placeIdResponse.results[0].formatted_address
                    )
                }
            } catch (e: Exception) {
                println("Error ${e.message}")
            }
        }
    }

    fun updateWeatherShowing(isShowing: Boolean) {
        _state.value = getCurrentState().copy(isWeatherShowing = isShowing)
        _state.value = getCurrentState().copy(listOfCities = emptyList())
    }

    fun decideTextAccordingToUVIndex(): String {
        return when (val uvIndex = state.value.uvIndex) {
            null -> "UV index is not available"
            in 0.0..0.5 -> "In ${state.value.chosenCity}. You can try to sunbathe but nothing will happen"
            in 0.51..2.5 -> "In ${state.value.chosenCity}. You can try to sunbathe but not much will happen"
            in 2.51..5.5 -> "In ${state.value.chosenCity}. You will get a tan after a while, maybe wear some sunscreen"
            in 5.51..7.5 -> "In ${state.value.chosenCity}. The UV index is high, wear sunscreen and get that tan"
            in 7.51..10.5 -> "In ${state.value.chosenCity}. Be careful out there, the sun is shining and the UV index is very high"
            else -> "In ${state.value.chosenCity}. UV index is too high to safely sunbathe so keep yourself in the shade"
        }
    }
}





