package com.example.sunnytime.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.sunnytime.api.city.autocompleteModel.Prediction
import com.example.sunnytime.viewmodel.MainViewModel


@Composable
fun MainContent(viewModel: MainViewModel) {
    val state by viewModel.state.collectAsState()

    SearchBar(cities = state.listOfCities, viewModel = viewModel)
}

@Composable
fun SearchBar(
    cities: List<Prediction>,
    viewModel: MainViewModel
) {
    var query by remember { mutableStateOf("") }

    Column {
        TextField(
            value = query,
            onValueChange = {
                query = it
                viewModel.onSearch(searchText = it)
                println("LISTOF: $cities")
            },
            label = { Text("Search") },
            modifier = Modifier.fillMaxWidth()
        )
        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(vertical = 4.dp)
        ) {
            items(cities.size) {
                SearchItem(city = cities[it])
            }
        }
    }
}

@Composable
fun SearchItem(city: Prediction) {
    val viewModel = MainViewModel()

    Text(
        text = city.description,
        modifier = Modifier.clickable { viewModel.onCityClicked(city.place_id) })
}



