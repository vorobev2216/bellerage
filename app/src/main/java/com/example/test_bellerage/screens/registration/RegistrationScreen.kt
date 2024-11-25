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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.example.test_bellerage.R
import com.example.test_bellerage.MainActivity
import com.example.test_bellerage.network.GitHubService
import com.example.test_bellerage.screens.registration.DTO.UserLogInDTO
import com.example.test_bellerage.utils.SecureTokenManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Composable
fun RegistrationScreen(modifier: Modifier) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val tokenManager = remember { SecureTokenManager(context) }

    val retrofit = Retrofit.Builder()
        .baseUrl("https://api.github.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    val apiService = retrofit.create(GitHubService::class.java)
    var user = remember { mutableStateOf<UserLogInDTO?>(null) }

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

                    if (tokenEditText.text.toString() == "") {
                        Toast.makeText(context, "Упс! Введите ID пользователя", Toast.LENGTH_SHORT)
                            .show()
                    } else {

                        scope.launch {
                            withContext(Dispatchers.IO) {
                                try {
                                     user.value =
                                        apiService.getUser(tokenEditText.text.toString().toInt())

                                } catch (e: Exception) {
                                    Log.d(
                                        "RRR",
                                        "Ошибка: ${e.message}"
                                    )


                                }
                            }
                        }
                        val intent = Intent(context, MainActivity::class.java).apply {
//                            putExtra("login", user.value!!.login)
//                            putExtra("followers", user.value!!.followers)
//                            putExtra("repositories", user.value!!.public_repos)
//                            putExtra("image", user.value!!.avatar_url)
                        }
                        context.startActivity(intent)
                        tokenManager.storeToken(userId.toInt())
                    }
                }
            }
        )
    }
}