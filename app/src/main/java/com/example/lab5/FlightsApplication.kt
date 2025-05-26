package com.example.lab5

import android.app.Application
import com.example.lab5.data.FlightsDatabase

class FlightsApplication : Application() {
    val database: FlightsDatabase by lazy { FlightsDatabase.getDatabase(this) }
}