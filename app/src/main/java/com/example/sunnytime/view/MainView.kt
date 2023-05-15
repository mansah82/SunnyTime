package com.example.sunnytime.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sunnytime.R
import com.example.sunnytime.api.city.autocompleteModel.Prediction
import com.example.sunnytime.viewmodel.MainViewModel
import com.example.sunnytime.viewmodel.model.State

@Composable
fun MainContent(viewModel: MainViewModel) {
    val state by viewModel.state.collectAsState()

    Background()

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        if (!state.isWeatherShowing) {
            SearchBar(cities = state.listOfCities, viewModel = viewModel)

        } else {
            WeatherContent(state, viewModel)
        }
    }
}

@Composable
private fun Background() {

    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
        Image(
            painter = painterResource(id = R.drawable.background),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillHeight,
        )
    }
}

@Composable
fun Date(viewModel: MainViewModel, state: State) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        if (state.dateOffset > -1) {
            Icon(
                painter = painterResource(id = R.drawable.chevron_left_solid),
                contentDescription = null,
                modifier = Modifier
                    .clickable {
                        viewModel.updateDateOffset(offset = state.dateOffset - 1)
                    }
                    .size(20.dp),
                tint = Color.White
            )
        }
        Text(text = state.dayOfTheWeek, color = Color.White, fontSize = 30.sp)
        if (state.dateOffset < 4)
            Icon(
                painter = painterResource(id = R.drawable.chevron_left_solid),
                contentDescription = null,
                modifier = Modifier
                    .size(20.dp)
                    .clickable {
                        viewModel.updateDateOffset(offset = state.dateOffset + 1)
                    }
                    .rotate(180f),
                tint = Color.White
            )
    }
}

@Composable
fun WeatherContent(state: State, viewModel: MainViewModel) {
    Column(
        modifier = Modifier
            .background(MaterialTheme.colors.primary)
            .padding(5.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {
        Date(viewModel = viewModel, state = state)
        Spacer(modifier = Modifier.fillMaxSize(0.2f))
        Text(text = viewModel.decideTextAccordingToUVIndex(), fontSize = 30.sp, color = Color.White)
        Spacer(modifier = Modifier.fillMaxSize(0.1f))
        Text(text = "The UV Index is: ${state.uvIndex}", fontSize = 30.sp, color = Color.White)
        Spacer(modifier = Modifier.fillMaxSize(0.3f))
        Button(
            onClick = { viewModel.updateWeatherShowing(isShowing = false) }) {
            Text(text = "Press here to search again")
        }
    }
}

@Composable
fun SearchBar(
    cities: List<Prediction>,
    viewModel: MainViewModel,
) {
    var query by remember { mutableStateOf("") }
    Column(modifier = Modifier.fillMaxWidth()) {

        TextField(
            value = query,
            onValueChange = {
                query = it
                viewModel.onSearch(searchText = it)
            },
            textStyle = TextStyle(fontSize = 15.sp),
            label = { Text(text = "Search for a city", fontSize = 15.sp) },
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(20.dp))
                .background(MaterialTheme.colors.background),
        )
        if (cities.isNotEmpty()) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(20.dp))
                    .background(MaterialTheme.colors.onBackground),
                contentPadding = PaddingValues(vertical = 4.dp)
            ) {
                items(cities.size) {
                    SearchItem(city = cities[it], viewModel)
                }
            }
        }
    }
}

@Composable
fun SearchItem(city: Prediction, viewModel: MainViewModel) {

    Text(
        text = city.description,
        fontSize = 15.sp,
        modifier = Modifier
            .clickable {
                viewModel.onCityClicked(city.place_id)
            }
            .padding(5.dp)
    )

}



