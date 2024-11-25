package com.example.test_bellerage.navigation


import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.sp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.test_bellerage.R
import com.guru.fontawesomecomposelib.FaIcon
import com.guru.fontawesomecomposelib.FaIconType
import com.guru.fontawesomecomposelib.FaIcons

sealed class BottomBarScreen(
    val route: String,
    val title: String,
    val icon: FaIconType.SolidIcon
) {
    object Home : BottomBarScreen(
        route = "home",
        title = "Users",
        icon = FaIcons.Bars
    )

    object WebView : BottomBarScreen(
        route = "settings",
        title = "WebView",
        icon = FaIcons.Bolt
    )

    object Profile : BottomBarScreen(
        route = "profile",
        title = "Profile",
        icon = FaIcons.User
    )

}

val FontAwesome = FontFamily(
    Font(R.font.fontawesome5brandsregular400)
)

@Composable
fun BottomBar(navController: NavHostController, screens: List<BottomBarScreen>) {

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    NavigationBar {
        screens.forEach { screen ->
            val selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true
            BottomNavigationItem(
                icon = {
                    FaIcon(
                        faIcon = screen.icon,
                        tint = if (selected) Color.White else Color.DarkGray,
                        modifier = Modifier.semantics { testTag = screen.title }
                    )
                },
                label = {
                    if (selected) Text(
                        text = screen.title,
                        fontFamily = FontAwesome
                    )
                },
                selected = selected,
                onClick = {
                    navController.navigate(screen.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
            )
        }
    }
}

@Composable
fun FontAwesomeIcon(icon: String, modifier: Modifier = Modifier, selected: Boolean = false) {
    Text(
        text = icon,
        style = TextStyle(
            fontFamily = FontAwesome,
            fontSize = 20.sp,
            color = if (selected) Color.White else Color.DarkGray
        ),
        modifier = modifier
    )
}


