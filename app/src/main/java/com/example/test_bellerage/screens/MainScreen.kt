package com.example.test_bellerage.screens



import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.navigation.compose.*
import com.example.test_bellerage.navigation.BottomBar

import com.example.test_bellerage.navigation.BottomNavGraph
import com.example.test_bellerage.screens.registration.viewmodel.RegistrationViewModel

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = { BottomBar(navController = navController) }
    ) { innerPaddig ->
        BottomNavGraph(navController = navController,)
    }
}

