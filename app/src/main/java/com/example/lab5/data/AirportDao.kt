package com.example.lab5.data

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface AirportDao {
    @Query("SELECT * FROM airport WHERE iata_code = :code")
    fun getAirportByCode(code: String): Flow<Airport?>

    @Query("SELECT * FROM airport")
    fun getAllAirports(): Flow<List<Airport>>

    @Query("SELECT * FROM airport WHERE id != :id")
    fun getAllDestinations(id: Int): Flow<List<Airport>>
}