package com.example.lab5.data

import android.content.Context
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [Airport::class, Favorite::class],
    version = 2,
    autoMigrations = [
        AutoMigration (from = 1, to = 2)
    ]
)
abstract class FlightsDatabase : RoomDatabase() {
    abstract fun airportDao(): AirportDao
    abstract fun favoriteDao(): FavoriteDao

    companion object {
        @Volatile
        private var Instance: FlightsDatabase? = null

        fun getDatabase(context: Context): FlightsDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, FlightsDatabase::class.java, "flight_search.db")
                    .createFromAsset("flight_search.db")
                    .build()
                    .also { Instance = it }
            }
        }
    }
}