package com.example.lab5.ui.screens

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.room.Room
import com.example.lab5.data.Airport

class AirportsViewModel : ViewModel() {
    val airports = mutableStateListOf<Airport>()


}