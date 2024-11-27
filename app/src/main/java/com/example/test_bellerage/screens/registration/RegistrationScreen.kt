package com.example.test_bellerage.screens.registration

import android.content.Intent
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.example.test_bellerage.R
import com.example.test_bellerage.MainActivity
import com.example.test_bellerage.appComponent
import com.example.test_bellerage.utils.SecureTokenManager



@Composable
fun RegistrationScreen(modifier: Modifier) {
    val context = LocalContext.current
    val tokenManager = remember { SecureTokenManager(context) }
    val gitHubService = context.appComponent.gitHubService()

    Scaffold { p ->
        AndroidView(
            modifier = modifier
                .fillMaxSize()
                .padding(p),
            factory = { context ->
                LayoutInflater.from(context).inflate(R.layout.registration_layout, null, false)
            },
            update = { view ->
                val tokenEditText = view.findViewById<EditText>(R.id.editTextToken)
                val loginButton = view.findViewById<Button>(R.id.buttonLogin)

                loginButton.setOnClickListener {
                    val userId = tokenEditText.text.toString()

                    if (tokenEditText.text.isEmpty()) {
                        Toast.makeText(context, "Упс! Введите ID пользователя", Toast.LENGTH_SHORT)
                            .show()
                    } else {
                        val intent = Intent(context, MainActivity::class.java)
                        context.startActivity(intent)
                        tokenManager.storeToken(userId)
                    }
                }
            }
        )
    }
}