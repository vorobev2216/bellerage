package com.example.test_bellerage

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.ViewModelProvider
import com.example.test_bellerage.screens.MainScreen
import com.example.test_bellerage.screens.registration.viewmodel.RegistrationViewModel
import com.example.test_bellerage.ui.theme.Test_bellerageTheme

class SignInActivity : ComponentActivity() {
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