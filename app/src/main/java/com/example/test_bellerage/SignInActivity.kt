package com.example.test_bellerage

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.example.test_bellerage.screens.registration.RegistrationScreen
import com.example.test_bellerage.ui.theme.Test_bellerageTheme

class SignInActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Test_bellerageTheme {
                Scaffold { i ->
                    RegistrationScreen(modifier = Modifier.padding(i))
                }
            }
        }
    }
}









