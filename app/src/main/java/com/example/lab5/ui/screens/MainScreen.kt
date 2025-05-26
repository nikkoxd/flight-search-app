package com.example.lab5.ui.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.lab5.data.Favorite
import com.example.lab5.ui.FavoriteCard
import com.example.lab5.ui.FlightCard
import com.example.lab5.ui.FlightSearchBar
import com.example.lab5.ui.FlightsViewModel

@Composable
fun MainScreen(viewModel: FlightsViewModel) {
    val destinations by viewModel.destinations.collectAsState()
    val favorites by viewModel.favorites.collectAsState()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            FlightSearchBar(viewModel = viewModel, modifier = Modifier.fillMaxWidth())
        }
    ) { innerPadding ->
        LazyColumn (modifier = Modifier.padding(innerPadding).padding(16.dp)) {
            if (viewModel.selectedAirport.value != null) {
                items(destinations) { destination ->
                    FlightCard(
                        departureAirport = viewModel.selectedAirport.value!!,
                        destinationAirport = destination,
                        onClick = {
                            viewModel.addOrRemoveFavorite(
                                Favorite(
                                departureCode = viewModel.selectedAirport.value!!.iataCode,
                                destinationCode = destination.iataCode
                            )
                            )
                        },
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                }
            } else {
                items(favorites) { favorite ->
                    FavoriteCard(
                        departureCode = favorite.departureCode,
                        destinationCode = favorite.destinationCode,
                        onClick = {
                            viewModel.removeFavorite(favorite)
                        },
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                }
            }
        }
    }
}