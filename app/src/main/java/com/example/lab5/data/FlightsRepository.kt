package com.example.lab5.data

import kotlinx.coroutines.flow.Flow

interface FlightsRepository {
    fun getAirportByCode(code: String): Flow<Airport?>
    fun getAirportsByNameOrCode(name: String): Flow<List<Airport>>
    fun getAllAirportsStream(): Flow<List<Airport>>
    fun getAllDestinationsStream(id: Int): Flow<List<Airport>>

    fun getAllFavoritesStream(): Flow<List<Favorite>>
    fun getFavorite(destinationCode: String, departureCode: String): Flow<Favorite?>
    suspend fun addFavorite(favorite: Favorite)
    suspend fun removeFavorite(favorite: Favorite)
}