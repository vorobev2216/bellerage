package com.example.test_bellerage.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.test_bellerage.screens.ProfileScreen
import com.example.test_bellerage.screens.WebViewScreen
import com.example.test_bellerage.screens.users.UsersScreen


@Composable
fun BottomNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = BottomBarScreen.Home.route
    ) {

        composable(route = BottomBarScreen.Home.route) {
            UsersScreen(navController)
        }
        composable(route = BottomBarScreen.Profile.route) {
            ProfileScreen()
        }
        composable(route = BottomBarScreen.WebView.route) {
            WebViewScreen()
        }

    }
}


