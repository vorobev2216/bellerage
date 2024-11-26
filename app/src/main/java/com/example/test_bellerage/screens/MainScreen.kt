package com.example.test_bellerage.screens


import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.navigation.compose.*
import com.example.test_bellerage.appComponent
import com.example.test_bellerage.navigation.BottomBar
import com.example.test_bellerage.navigation.BottomBarScreen
import com.example.test_bellerage.navigation.BottomNavGraph
import com.example.test_bellerage.viewmodel.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    val context = LocalContext.current
    val viewModel = remember {
        ViewModelProvider(
            context as ViewModelStoreOwner,
            context.appComponent.mainViewModelFactory()
        )[MainViewModel::class.java]
    }
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
            BottomBar(navController = navController, screens)
        },
        topBar = {
            if (currentRoute == "details") {
                TopAppBar(title = { Text(text = "${viewModel.user.observeAsState().value}") }, navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.KeyboardArrowLeft, "back")
                    }
                })
            }
        }
    ) { innerPadding ->
        BottomNavGraph(navController = navController, modifier = Modifier.padding(innerPadding))
    }
}

