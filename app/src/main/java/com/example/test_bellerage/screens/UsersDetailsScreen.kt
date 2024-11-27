package com.example.test_bellerage.screens

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.example.test_bellerage.R
import com.example.test_bellerage.appComponent
import com.example.test_bellerage.screens.users.DTO.UserDTORecycler
import com.example.test_bellerage.utils.SecureTokenManager
import com.example.test_bellerage.viewmodel.MainViewModel
import com.squareup.picasso.Picasso

@Composable
fun UsersDetailsScreen() {
    val context = LocalContext.current
    val token = remember { SecureTokenManager(context) }
    val viewModel = remember {
        ViewModelProvider(
            context as ViewModelStoreOwner,
            context.appComponent.mainViewModelFactory()
        )[MainViewModel::class.java]
    }
    val followers = viewModel.followers.observeAsState(emptyList())
    viewModel.userDetails.value?.let {
        viewModel.getFollowers(
            it.login,
            token = token.retrieveToken()!!
        )
    }

    UserList(users = followers.value)

}


@Composable
fun UserCard(
    login: String,
    avatarUrl: String,
    followerCount: String,
) {
    var bitmap by remember { mutableStateOf<Bitmap?>(null) }
    LaunchedEffect(Unit) {
        Picasso.get()
            .load(avatarUrl)
            .placeholder(R.drawable.profile_image)
            .into(object : com.squareup.picasso.Target {
                override fun onBitmapLoaded(loadedBitmap: Bitmap?, from: Picasso.LoadedFrom?) {
                    bitmap = loadedBitmap
                }

                override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {
                    e?.printStackTrace()
                }

                override fun onPrepareLoad(placeHolderDrawable: Drawable?) {
                }
            })
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        shape = RoundedCornerShape(4.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {

            bitmap?.asImageBitmap()?.let { Image(it, "", modifier = Modifier.size(48.dp)) }


            Spacer(modifier = Modifier.width(16.dp))

            Column {
                Text(
                    text = login,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "$followerCount followers",
                    fontSize = 14.sp
                )
            }
        }
    }
}


@Composable
fun UserList(users: List<UserDTORecycler>) {
    val context = LocalContext.current
    val viewModel = remember {
        ViewModelProvider(
            context as ViewModelStoreOwner,
            context.appComponent.mainViewModelFactory()
        )[MainViewModel::class.java]
    }
    val lifecycleOwner = LocalLifecycleOwner.current

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        items(users, key = { it.id }) { user ->
            var followerCount = remember { mutableStateOf<String?>(null) }

            DisposableEffect(user.login) {
                val observer = Observer<List<UserDTORecycler>?> { followers ->
                    followerCount.value = followers?.size?.toString() ?: "0"
                }
                viewModel.followers.observe(lifecycleOwner, observer)

                onDispose {
                    viewModel.followers.removeObserver(observer)
                }
            }

            UserCard(
                login = user.login,
                avatarUrl = user.avatar_url,
                followerCount = followerCount.value ?: "Loading..."
            )
        }
    }
}


