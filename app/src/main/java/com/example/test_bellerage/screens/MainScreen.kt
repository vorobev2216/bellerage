package com.example.test_bellerage.screens


import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.*
import com.example.test_bellerage.navigation.BottomBar
import com.example.test_bellerage.navigation.BottomBarScreen
import com.example.test_bellerage.navigation.BottomNavGraph

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val screens = listOf(
        BottomBarScreen.Home,
        BottomBarScreen.WebView,
        BottomBarScreen.Profile,
    )
    Scaffold(
        bottomBar = {
            if (currentRoute != "details") {
                BottomBar(navController = navController, screens)
            }
        },
    ) { innerPadding ->
        BottomNavGraph(navController = navController, modifier = Modifier.padding(innerPadding))
    }
}

