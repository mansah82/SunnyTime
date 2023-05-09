package com.example.sunnytime.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.example.sunnytime.viewmodel.MainViewModel
import com.example.sunnytime.viewmodel.model.State


@Composable
fun MainContent(viewModel: MainViewModel) {
    val state by viewModel.state.collectAsState()


    SearchContent(
        viewModel = viewModel,
        state = state
    )
    

}

@Composable
fun SearchContent(
    viewModel: MainViewModel,
    state: State
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Button(
            onClick = {
                viewModel.getUVIndex()
            }
        ) {
            Text("Show Weather")
        }
        Text(text = "${state.uvIndex}")
    }
}

