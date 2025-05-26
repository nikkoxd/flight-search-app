package com.example.lab5

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge

import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.lab5.ui.FlightsViewModel
import com.example.lab5.ui.screens.MainScreen
import com.example.lab5.ui.theme.Lab5Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val viewModel: FlightsViewModel = viewModel(factory = FlightsViewModel.factory)

            Lab5Theme {
                MainScreen(viewModel)
            }
        }
    }
}
