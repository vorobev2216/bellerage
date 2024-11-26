package com.example.test_bellerage.screens

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.example.test_bellerage.SignInActivity
import com.example.test_bellerage.R
import com.example.test_bellerage.appComponent
import com.example.test_bellerage.utils.SecureTokenManager
import com.example.test_bellerage.viewmodel.MainViewModel
import com.squareup.picasso.Picasso


@Composable
fun ProfileScreen() {

    val context = LocalContext.current as Activity
    val viewModel = remember {
        ViewModelProvider(
            context as ViewModelStoreOwner,
            context.appComponent.mainViewModelFactory()
        )[MainViewModel::class.java]
    }
    var tokenManager = remember { SecureTokenManager(context) }
    val scrollState = rememberScrollState()
    val followers = context.intent.getIntExtra("followers", 0)
    val repo = context.intent.getIntExtra("repositories", 0)
    val login = context.intent.getStringExtra("login")
    val image = context.intent.getStringExtra("image")
    var bitmap by remember { mutableStateOf<Bitmap?>(null) }


    LaunchedEffect(image) {
        image?.let { url ->
            Picasso.get()
                .load(url)
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
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 20.dp)
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        bitmap?.let {
            Image(
                bitmap = it.asImageBitmap(),
                contentDescription = "Profile Image",
                modifier = Modifier
                    .padding(start = 10.dp, end = 10.dp)
                    .size(200.dp)
            )
        } ?: CircularProgressIndicator()

        Text(
            text = "$login",
            fontFamily = FontFamily(Font(R.font.fontawesome5brandsregular400))
        )

        Text(
            text = "$followers followers",
            fontFamily = FontFamily(Font(R.font.fontawesome5brandsregular400))
        )

        Text(
            text = "$repo repositories",
            fontFamily = FontFamily(Font(R.font.fontawesome5brandsregular400))
        )
        Button(
            onClick = {
                tokenManager.deleteToken()

                val intent = Intent(context, SignInActivity::class.java)
                context.startActivity(intent)
            },
            modifier = Modifier,
            colors = ButtonColors(
                containerColor = Color.Transparent,
                contentColor = Color.Red,
                disabledContentColor = Color.Transparent,
                disabledContainerColor = Color.Transparent
            )
        ) {
            Text(
                text = "Log out",
                fontFamily = FontFamily(Font(R.font.fontawesome5brandsregular400)),
                color = Color.Red
            )
        }

    }
}