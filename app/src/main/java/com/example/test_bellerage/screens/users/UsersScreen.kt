package com.example.test_bellerage.screens.users



import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
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
    val users by viewModel.users.observeAsState(emptyList())
    LaunchedEffect(since) {
        coroutineScope.launch {
            viewModel.getUsers(since.intValue, 40)
        }
    }

    visibleUsers.value = users.take(10)

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
                        users = visibleUsers.value.toMutableList(),
                        onUserClick = { user ->
                            Log.d("RRR", "User clicked: ${user.login}")
                        },
                        viewModel = viewModel,
                        navController = navController
                    )
                }
            },
            update = { recyclerView ->
                (recyclerView.adapter as UserAdapter).updateUsers(visibleUsers.value)
            }
        )
    }
}
