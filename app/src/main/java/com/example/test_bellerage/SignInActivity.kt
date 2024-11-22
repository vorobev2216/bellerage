package com.example.test_bellerage

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.example.test_bellerage.screens.registration.RegistrationScreen
import com.example.test_bellerage.ui.theme.Test_bellerageTheme
import com.example.test_bellerage.utils.SecureTokenManager

class SignInActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Test_bellerageTheme {
                Scaffold { i ->
                    val context = LocalContext.current
                    val tokenManager = remember { SecureTokenManager(context) }


                    if (tokenManager.hasToken()) {
                        val intent = Intent(context, MainActivity::class.java)
                        context.startActivity(intent)
                    }
                    RegistrationScreen(modifier = Modifier.padding(i))
                }
            }
        }
    }
}









