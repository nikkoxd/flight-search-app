package com.example.lab5.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface FavoriteDao {
    @Insert
    suspend fun insertFavorites(vararg favorites: Favorite)

    @Delete
    suspend fun deleteFavorites(vararg favorites: Favorite)

    @Query("SELECT * FROM favorite")
    suspend fun getAll(): List<Favorite>
}