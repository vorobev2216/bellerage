package com.example.test_bellerage.screens.registration

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.example.test_bellerage.R
import com.example.test_bellerage.SignInActivity
import com.example.test_bellerage.network.GitHubApiService
import com.example.test_bellerage.screens.registration.DTO.UserLogInDTO
import com.example.test_bellerage.screens.registration.viewmodel.RegistrationViewModel
import com.example.test_bellerage.utils.SecureTokenManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Composable
fun RegistrationScreen() {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    val tokenManager = remember { SecureTokenManager(context) }
    val retrofit = Retrofit.Builder()
        .baseUrl("https://api.github.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    val apiService = retrofit.create(GitHubApiService::class.java)

    Scaffold { p ->
        AndroidView(
            modifier = Modifier
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

                    if (tokenEditText.text.toString() == "") {
                        Toast.makeText(context, "Упс! Введите ID пользователя", Toast.LENGTH_SHORT).show()
                    } else {

                        scope.launch {
                            try {
                                val user: UserLogInDTO = apiService.getUser(tokenEditText.text.toString().toInt())

                                    val intent = Intent(context, SignInActivity::class.java).apply {
                                        putExtra("login", user.login)
                                        putExtra("followers", user.followers)
                                        putExtra("repositories", user.public_repos)
                                    }
                                    context.startActivity(intent)
                                tokenManager.storeToken(userId.toInt())

                            } catch (e: Exception) {
                                    Toast.makeText(context, "Ошибка: ${e.message}", Toast.LENGTH_SHORT).show()

                            }
                        }
                    }
                }
            }
        )
    }
}