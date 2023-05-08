package com.example.sunnytime.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.sunnytime.viewmodel.MainViewModel


@Composable
fun MainContent(viewModel: MainViewModel) {
    val state by viewModel.state.collectAsState()

    if (state.isWeatherShowing) {
        WeatherContent()
    } else {
        SearchContent(
            viewModel = viewModel
        )
    }

}

@Composable
fun SearchContent(
    viewModel: MainViewModel
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Button(
            onClick = { viewModel.updateWeatherShowing(true) }
        ) {
            Text("Show Weather")
        }
    }
}

@Composable
fun WeatherContent() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Green)
    )
}