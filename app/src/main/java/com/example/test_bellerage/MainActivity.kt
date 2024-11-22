package com.example.test_bellerage

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.test_bellerage.screens.MainScreen
import com.example.test_bellerage.ui.theme.Test_bellerageTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Test_bellerageTheme {
                MainScreen()
            }
        }
    }
}