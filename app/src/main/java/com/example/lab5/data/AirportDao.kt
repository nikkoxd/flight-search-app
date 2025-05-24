package com.example.lab5.data

import androidx.room.Dao
import androidx.room.Query

@Dao
interface AirportDao {
    @Query("SELECT * FROM airport")
    suspend fun getAll(): List<Airport>
}