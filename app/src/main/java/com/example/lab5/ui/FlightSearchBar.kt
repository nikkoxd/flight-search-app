package com.example.lab5.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.traversalIndex
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FlightSearchBar(viewModel: FlightsViewModel, modifier: Modifier = Modifier) {
    Box (modifier = modifier) {
        SearchBar(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .semantics { traversalIndex = 0f },
            query = viewModel.query.value,
            onQueryChange = {
                viewModel.query.value = it
                viewModel.setQuery(it)
                viewModel.getResults(it)
            },
            onSearch = {
                if (viewModel.query.value == "") {
                    viewModel.selectedAirport.value = null
                }
                viewModel.searchActive.value = false
            },
            active = viewModel.searchActive.value,
            onActiveChange = { viewModel.searchActive.value = it },
            placeholder = {
                Text("Enter departure airport")
            },
            leadingIcon = {
                Icon(
                    Icons.Rounded.Search,
                    contentDescription = "Search",
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
        ) {
            LazyColumn {
                items(viewModel.results.value) { result ->
                    Row (modifier = Modifier
                        .clickable {
                            viewModel.searchActive.value = false
                            viewModel.setQuery(result.iataCode)
                            viewModel.query.value = result.iataCode
                            viewModel.selectedAirport.value = result
                            viewModel.getDestinations(viewModel.selectedAirport.value!!.id)
                        }
                        .padding(16.dp)
                    ) {
                        Text(
                            text = result.iataCode,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(end = 8.dp)
                        )
                        Text(result.name)
                    }
                }
            }
        }
    }
}