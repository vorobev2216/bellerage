package com.example.test_bellerage.screens

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
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.example.test_bellerage.R
import com.example.test_bellerage.SignInActivity
import com.example.test_bellerage.utils.SecureTokenManager

@Composable
fun RegistrationScreen() {
    val context = LocalContext.current
    val tokenManager = remember {
        SecureTokenManager(context)
    }
    Scaffold { p ->
        AndroidView(
            modifier = Modifier
                .fillMaxSize()
                .padding(p),
            factory = { context ->
                LayoutInflater.from(context).inflate(R.layout.registration_layout, null, false)

            }, update = { view ->

                val tokenEditText = view.findViewById<EditText>(R.id.editTextToken)
                val loginButton = view.findViewById<Button>(R.id.buttonLogin)


                loginButton.setOnClickListener {
                    if (tokenEditText.text.toString() == "") {
                        Toast.makeText(context, "Упс! Введите токен)", Toast.LENGTH_SHORT).show()
                    } else{
                        tokenManager.storeToken(tokenEditText.text.toString())

                        val intent = Intent(context, SignInActivity::class.java)
                        context.startActivity(intent)
                    }
                }
            }
        )
    }

}