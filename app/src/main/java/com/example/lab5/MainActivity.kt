package com.example.lab5

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.traversalIndex
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.room.Room
import com.example.lab5.data.Airport
import com.example.lab5.data.AppDatabase
import com.example.lab5.ui.theme.Lab5Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Lab5Theme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        FlightSearchBar()
                    }
                ) { innerPadding ->
                    Column (modifier = Modifier.padding(innerPadding)) {
                        Text("1213")
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FlightSearchBar(modifier: Modifier = Modifier) {
    var query by rememberSaveable { mutableStateOf("") }
    var active by rememberSaveable { mutableStateOf(false) }

    Box (modifier = modifier) {
        SearchBar(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .semantics { traversalIndex = 0f },
            query = query,
            onQueryChange = { query = it },
            onSearch = {
                active = false
            },
            active = active,
            onActiveChange = { active = it },
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
            Column {
                Text("bleh")
            }
        }
    }
}

@Composable
fun Flight(
    departureAirport: Airport,
    arrivalAirport: Airport,
    onClick: () -> Unit,
    isClicked: Boolean,
    modifier: Modifier = Modifier
) {
    Card(modifier = modifier) {
        Column(modifier = Modifier.padding(8.dp)) {
            Text("DEPART")
            Row() {
                Text(departureAirport.iataCode)
                Text(departureAirport.name)
            }
            Text("ARRIVE")
            Row {
                Text(arrivalAirport.iataCode)
                Text(arrivalAirport.name)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Lab5Theme {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                FlightSearchBar(modifier = Modifier.fillMaxWidth())
            }
        ) { innerPadding ->
            Column (modifier = Modifier.padding(innerPadding).padding(12.dp).fillMaxWidth()) {
                Flight(
                    departureAirport = Airport(
                        name = "aboba",
                        id = 1,
                        iataCode = "ABC",
                        passengers = 1,
                    ),
                    arrivalAirport = Airport(
                        name = "bob",
                        id = 2,
                        iataCode = "XYZ",
                        passengers = 2,
                    ),
                    onClick = {},
                    isClicked = false,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}