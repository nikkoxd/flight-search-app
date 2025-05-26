package com.example.lab5.ui

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.lab5.FlightsApplication
import com.example.lab5.data.Airport
import com.example.lab5.data.Favorite
import com.example.lab5.data.FlightsRepository
import com.example.lab5.data.OfflineFlightsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

class FlightsViewModel(
    private val flightsRepository: FlightsRepository
) : ViewModel() {
    var query = mutableStateOf("")
    var searchActive = mutableStateOf(false)

    var selectedAirport = mutableStateOf<Airport?>(null)

    private val _results = MutableStateFlow<List<Airport>>(emptyList())
    val results: StateFlow<List<Airport>> = _results

    private val _destinations = MutableStateFlow<List<Airport>>(emptyList())
    val destinations: StateFlow<List<Airport>> = _destinations

    private val _favorites = MutableStateFlow<List<Favorite>>(emptyList())
    val favorites: StateFlow<List<Favorite>> = _favorites

    init {
        getResults("")
        getFavorites()
    }

    fun getResults(query: String) {
        viewModelScope.launch {
            flightsRepository.getAirportsByNameOrCode("%$query%").collect { results ->
                _results.value = results
            }
        }
    }

    fun getDestinations(id: Int) {
        viewModelScope.launch {
            flightsRepository.getAllDestinationsStream(id).collect { results ->
                _destinations.value = results
            }
        }
    }

    fun getFavorites() {
        viewModelScope.launch {
            flightsRepository.getAllFavoritesStream().collect { results ->
                _favorites.value = results
                println(results)
            }
        }
    }

    fun addOrRemoveFavorite(favorite: Favorite) {
        viewModelScope.launch {
            getFavorites()
            println("Favorites: $favorites.value")
            println("Trying to delete: $favorite")
            val result = flightsRepository.getFavorite(favorite.departureCode, favorite.destinationCode).first()
            if (result == null) {
                addFavorite(favorite)
            } else {
                removeFavorite(result)
            }
        }
    }

    fun addFavorite(favorite: Favorite) {
        viewModelScope.launch {
            flightsRepository.addFavorite(favorite)
        }
    }

    fun removeFavorite(favorite: Favorite) {
        viewModelScope.launch {
            flightsRepository.removeFavorite(favorite)
        }
    }

    companion object {
        val factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as FlightsApplication)
                val repository = OfflineFlightsRepository(application.database.airportDao(), application.database.favoriteDao())

                FlightsViewModel(repository)
            }
        }
    }
}