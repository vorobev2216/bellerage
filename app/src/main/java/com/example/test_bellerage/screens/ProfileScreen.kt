package com.example.test_bellerage.screens

import android.app.Activity
import android.content.Intent
import android.hardware.camera2.params.ColorSpaceTransform
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import com.example.test_bellerage.MainActivity
import com.example.test_bellerage.R
import com.example.test_bellerage.screens.registration.viewmodel.RegistrationViewModel
import com.example.test_bellerage.utils.SecureTokenManager


@Composable
fun ProfileScreen() {

    val context = LocalContext.current as Activity
    var tokenManager = remember { SecureTokenManager(context) }
    val scrollState = rememberScrollState()


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 20.dp)
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.profile_image),
            contentDescription = "Profile Logo",
            modifier = Modifier
                .padding(start = 10.dp, end = 10.dp)
                .size(200.dp)
        )
        val login = context.intent.getStringExtra("login")

        Text(
            text = "${login}",
            fontFamily = FontFamily(Font(R.font.fontawesome5brandsregular400))
        )
        val folowers = context.intent.getIntExtra("followers",0)
        val repo = context.intent.getIntExtra("repositories",0)
        Text(
            text = "$folowers followers",
            fontFamily = FontFamily(Font(R.font.fontawesome5brandsregular400))
        )

        Text(
            text = "${repo} repositories",
            fontFamily = FontFamily(Font(R.font.fontawesome5brandsregular400))
        )
        Button(
            onClick = {
                tokenManager.deleteToken()

                val intent = Intent(context, MainActivity::class.java)
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
                text = "Log out", fontFamily = FontFamily(Font(R.font.fontawesome5brandsregular400)), color = Color.Red
            )
        }

    }
}