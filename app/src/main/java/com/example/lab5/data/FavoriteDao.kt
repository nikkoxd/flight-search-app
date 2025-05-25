package com.example.lab5.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteDao {
    @Query("SELECT * FROM favorite")
    fun getAll(): Flow<List<Favorite>>

    @Insert
    suspend fun addFavorite(favorite: Favorite)

    @Delete
    suspend fun removeFavorite(favorite: Favorite)
}