package com.example.lab5.data

import kotlinx.coroutines.flow.Flow

class OfflineFlightsRepository(
    private val airportDao: AirportDao,
    private val favoriteDao: FavoriteDao
): FlightsRepository {
    override fun getAirportByCode(code: String): Flow<Airport?> = airportDao.getAirportByCode(code)
    override fun getAirportsByNameOrCode(name: String): Flow<List<Airport>> = airportDao.getAirportsByNameOrCode(name)
    override fun getAllAirportsStream(): Flow<List<Airport>> = airportDao.getAllAirports()
    override fun getAllDestinationsStream(id: Int): Flow<List<Airport>> = airportDao.getAllDestinations(id)

    override fun getAllFavoritesStream(): Flow<List<Favorite>> = favoriteDao.getAll()
    override fun getFavorite(destinationCode: String, departureCode: String): Flow<Favorite?> = favoriteDao.getFavorite(departureCode, destinationCode)
    override suspend fun addFavorite(favorite: Favorite) = favoriteDao.addFavorite(favorite)
    override suspend fun removeFavorite(favorite: Favorite) = favoriteDao.removeFavorite(favorite)
}