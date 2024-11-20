package com.example.test_bellerage

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.ViewModelProvider
import com.example.test_bellerage.screens.registration.RegistrationScreen
import com.example.test_bellerage.screens.registration.viewmodel.RegistrationViewModel
import com.example.test_bellerage.ui.theme.Test_bellerageTheme
import com.example.test_bellerage.utils.SecureTokenManager

// TODO тут вообще прикол с неймингами (RegScreen на Main, MainScreen на SignInAct)
// TODO enableEdgeToEdge????
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
//        installSplashScreen()
        setContent {
            Test_bellerageTheme {
                val context = LocalContext.current
                val tokenManager = remember { SecureTokenManager(context) }


                if (tokenManager.hasToken()) {
                    val intent = Intent(context, SignInActivity::class.java)
                    context.startActivity(intent)
                }
                RegistrationScreen()
            }
        }
    }
}















