package com.example.test_bellerage.screens.users

import android.util.Log
import android.widget.Toast

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavHostController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.test_bellerage.network.GitHubService
import com.example.test_bellerage.screens.users.DTO.UserDTORecycler
import com.example.test_bellerage.screens.users.adapter.UserAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


@Composable
fun UsersScreen(navController: NavHostController) {
    val coroutineScope = rememberCoroutineScope()
    var users = remember { mutableListOf<UserDTORecycler>() }
    var visibleUsers = remember { mutableStateOf<List<UserDTORecycler>>(emptyList()) }
    var since = remember {
        mutableIntStateOf(1)
    }


    val retrofit = Retrofit.Builder()
        .baseUrl("https://api.github.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val service = retrofit.create(GitHubService::class.java)

    LaunchedEffect(Unit) {
        coroutineScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    val call = service.getUsers(since.intValue,40)
                    users.clear()
                    users.addAll(call)
                    visibleUsers.value = users.take(10)
                } catch (e: Exception) {
                    Log.e("RRR", "Error: ${e.message}")
                }
            }
        }
    }

    val onLoadMore = rememberUpdatedState {
        if (visibleUsers.value.size < users.size) {
            val newSize = minOf(visibleUsers.value.size + 10, users.size)
            visibleUsers.value = users.take(newSize)
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxWidth(),
    ) { innerPadding ->
        AndroidView(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            factory = {
                RecyclerView(it).apply {
                    layoutManager = LinearLayoutManager(it).apply {

                        addOnScrollListener(object : RecyclerView.OnScrollListener() {
                            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                                super.onScrolled(recyclerView, dx, dy)
                                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                                val totalItemCount = layoutManager.itemCount
                                val lastVisibleItem = layoutManager.findLastVisibleItemPosition()

                                if (lastVisibleItem == totalItemCount - 1) {
                                    onLoadMore.value.invoke()
                                    since.value += 1
                                }
                            }
                        })
                    }
                    adapter = UserAdapter(visibleUsers.value.toMutableList()) { login ->
                        Log.d("RRR", login.toString())
                        if(login.followersUrl == null){
                            Toast.makeText(context,"Нет подписчиков", Toast.LENGTH_SHORT).show()
                        }

                    }
                }
            },
            update = { recyclerView ->
                (recyclerView.adapter as UserAdapter).updateUsers(visibleUsers.value)
            }
        )
    }
}

