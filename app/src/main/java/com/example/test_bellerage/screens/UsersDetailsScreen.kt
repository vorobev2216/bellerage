package com.example.test_bellerage.screens

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.test_bellerage.screens.users.DTO.UserDTORecycler
import com.squareup.picasso.Picasso

@Composable
fun UsersDetailsScreen(){
    val mockUsers = createMockUserList()

    UserList(users = mockUsers)
}


@Composable
fun UserCard(user: UserDTORecycler) {
    var bitmap by remember { mutableStateOf<Bitmap?>(null) }

    LaunchedEffect(Unit) {
            Picasso.get()
                .load(user.avatar_url)
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



            Spacer(modifier = Modifier.width(16.dp))

            Column {
                Text(
                    text = user.login,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "${user.followers_url} followers",
                    fontSize = 14.sp
                )

            }

        }
    }
}




@Composable
fun UserList(users: List<UserDTORecycler>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        items(users) { user ->
            UserCard(user)
        }
    }
}

fun createMockUserList(count: Int = 1000): List<UserDTORecycler> {
    val mockUsers = mutableListOf<UserDTORecycler>()
    for (i in 1..count) {
        mockUsers.add(
            UserDTORecycler(
                login = "User$i",
                id = i,
                node_id = "node_$i",
                avatar_url = "https:com/150",
                gravatar_id = "gravatar_$i",
                url = "user_url_$i",
                html_url = "user_html_url_$i",
                followers_url = "followers_url_$i",
                following_url = "following_url_$i",
                gists_url = "gists_url_$i",
                starred_url = "starred_url_$i",
                subscriptions_url = "subscriptions_url_$i",
                organizations_url = "organizations_url_$i",
                repos_url = "repos_url_$i",
                events_url = "events_url_$i",
                received_events_url = "received_events_url_$i",
                type = "User",
                site_admin = false
            )
        )
    }
    return mockUsers
}

