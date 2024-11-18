package com.example.test_bellerage.screens.users

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.test_bellerage.screens.users.adapter.UserAdapter

@Composable
fun UsersScreen() {


    val users = remember {
        listOf(
            User("User1", 20, "https://avatars.githubusercontent.com/u/1?v=4"),
            User("User2", 35, "https://avatars.githubusercontent.com/u/2?v=4"),
            User("User2", 35, "https://avatars.githubusercontent.com/u/2?v=4"),
            User("User2", 35, "https://avatars.githubusercontent.com/u/2?v=4"),
            User("User2", 35, "https://avatars.githubusercontent.com/u/2?v=4"),
            User("User2", 35, "https://avatars.githubusercontent.com/u/2?v=4"),
            User("User2", 35, "https://avatars.githubusercontent.com/u/2?v=4"),
            User("User2", 35, "https://avatars.githubusercontent.com/u/2?v=4"),
            User("User2", 35, "https://avatars.githubusercontent.com/u/2?v=4"),
            User("User2", 35, "https://avatars.githubusercontent.com/u/2?v=4"),
            User("User2", 35, "https://avatars.githubusercontent.com/u/2?v=4"),
            User("User2", 35, "https://avatars.githubusercontent.com/u/2?v=4"),
            User("User2", 35, "https://avatars.githubusercontent.com/u/2?v=4"),
            User("User2", 35, "https://avatars.githubusercontent.com/u/2?v=4"),
            User("User2", 35, "https://avatars.githubusercontent.com/u/2?v=4"),
            User("User2", 35, "https://avatars.githubusercontent.com/u/2?v=4"),
            User("User2", 35, "https://avatars.githubusercontent.com/u/2?v=4"),
            User("User2", 35, "https://avatars.githubusercontent.com/u/2?v=4"),
            User("User2", 35, "https://avatars.githubusercontent.com/u/2?v=4"),
            User("User2", 35, "https://avatars.githubusercontent.com/u/2?v=4"),
            User("User2", 35, "https://avatars.githubusercontent.com/u/2?v=4"),
            User("User2", 35, "https://avatars.githubusercontent.com/u/2?v=4"),
            User("User2", 35, "https://avatars.githubusercontent.com/u/2?v=4"),
        )
    }

    Scaffold(modifier = Modifier
        .fillMaxWidth(),
    ) { innerPadding ->
        AndroidView(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            factory = {
                RecyclerView(it).apply {
                    layoutManager = LinearLayoutManager(it)
                    adapter = UserAdapter(users) { login ->

                    }
                }

            }
        )
    }
}