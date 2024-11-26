package com.example.test_bellerage.screens.users



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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.navigation.NavHostController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.test_bellerage.appComponent
import com.example.test_bellerage.screens.users.DTO.UserDTORecycler
import com.example.test_bellerage.screens.users.adapter.UserAdapter
import com.example.test_bellerage.viewmodel.MainViewModel
import kotlinx.coroutines.launch



@Composable
fun UsersScreen(navController: NavHostController) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val users = remember { mutableListOf<UserDTORecycler>() }
    val visibleUsers = remember { mutableStateOf<List<UserDTORecycler>>(emptyList()) }
    val since = remember {
        mutableIntStateOf(1)
    }
    val viewModel = remember {
        ViewModelProvider(
            context as ViewModelStoreOwner,
            context.appComponent.mainViewModelFactory()
        )[MainViewModel::class.java]
    }
    LaunchedEffect(Unit) {
        coroutineScope.launch {
            viewModel.getUsers(since.intValue, 40)
        }
    }

    viewModel.users?.let { users.addAll(it) }
    visibleUsers.value = users.take(10)

//    LaunchedEffect(since) {
//        coroutineScope.launch {
//            withContext(Dispatchers.IO) {
//                try {
//                    val call = gitHubService.getUsers(since.intValue, 40)
//                    users.clear()
//                    users.addAll(call)
//                    visibleUsers.value = users.take(10)
//                } catch (e: Exception) {
//                    Log.e("RRR", "Error: ${e.message}")
//                }
//            }
//        }
//    }

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
                                val layoutManager =
                                    recyclerView.layoutManager as LinearLayoutManager
                                val totalItemCount = layoutManager.itemCount
                                val lastVisibleItem = layoutManager.findLastVisibleItemPosition()

                                if (lastVisibleItem == totalItemCount - 1) {
                                    onLoadMore.value.invoke()
                                    since.value += 1
                                }
                            }
                        })
                    }
                    adapter = UserAdapter(
                        visibleUsers.value.toMutableList(),
                        navController = navController
                    )
//                    { user ->
//                        if (user.followers_url == null) {
//                            Toast.makeText(context, "Нет подписчиков", Toast.LENGTH_SHORT).show()
//                        }
//                        composeView.setContent {
//                            UserProfileScreen()
//                        }
//                    }
                }
            },
            update = { recyclerView ->
                (recyclerView.adapter as UserAdapter).updateUsers(visibleUsers.value)
            }
        )
    }
}
