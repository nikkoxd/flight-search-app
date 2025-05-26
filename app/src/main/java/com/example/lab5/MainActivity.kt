package com.example.lab5

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.traversalIndex
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.lab5.data.Airport
import com.example.lab5.data.Favorite
import com.example.lab5.ui.FlightsViewModel
import com.example.lab5.ui.theme.Lab5Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val viewModel: FlightsViewModel = viewModel(factory = FlightsViewModel.factory)

            val destinations by viewModel.destinations.collectAsState()
            val favorites by viewModel.favorites.collectAsState()

            viewModel.getFavorites()
            Lab5Theme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        FlightSearchBar(viewModel = viewModel, modifier = Modifier.fillMaxWidth())
                    }
                ) { innerPadding ->
                    LazyColumn (modifier = Modifier.padding(innerPadding).padding(16.dp)) {
                        if (viewModel.selectedAirport.value != null) {
                            items(destinations) { destination ->
                                Flight(
                                    departureAirport = viewModel.selectedAirport.value!!,
                                    destinationAirport = destination,
                                    onClick = {
                                        viewModel.addOrRemoveFavorite(Favorite(
                                            departureCode = viewModel.selectedAirport.value!!.iataCode,
                                            destinationCode = destination.iataCode
                                        ))
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
        }
    }
}

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
                viewModel.getResults(viewModel.query.value)
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

@Composable
fun FavoriteCard(
    departureCode: String,
    destinationCode: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(modifier = modifier) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth().padding(16.dp)
        ) {
            Column {
                Text(
                    text = "DEPART",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = departureCode,
                    fontWeight = FontWeight.Bold,
                )
                Text(
                    text = "ARRIVE",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(top = 8.dp)
                )
                Text(
                    text = destinationCode,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(end = 8.dp)
                )
            }
            IconButton (
                onClick = onClick,
            ) {
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = "Favorite"
                )
            }
        }
    }
}

@Composable
fun Flight(
    departureAirport: Airport,
    destinationAirport: Airport,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(modifier = modifier) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth().padding(16.dp)
        ) {
            Column {
                Text(
                    text = "DEPART",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
                Row {
                    Text(
                        text = departureAirport.iataCode,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(end = 8.dp)
                    )
                    Text(departureAirport.name)
                }
                Text(
                    text = "ARRIVE",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(top = 8.dp)
                )
                Row {
                    Text(
                        text = destinationAirport.iataCode,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(end = 8.dp)
                    )
                    Text(destinationAirport.name)
                }
            }
            IconButton (
                onClick = onClick,
            ) {
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = "Favorite"
                )
            }
        }
    }
}