package com.example.test_bellerage.screens

import android.annotation.SuppressLint
import android.webkit.WebView
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.example.test_bellerage.utils.MyJavaScriptInterface

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun WebViewScreen() {

    AndroidView(
        modifier = Modifier.fillMaxSize(),
        factory = { context ->
            WebView(context).apply {

                settings.javaScriptEnabled = true

                addJavascriptInterface(MyJavaScriptInterface(context), "jsInterface")

                loadDataWithBaseURL(
                    null,
                    """
                    <!DOCTYPE html>
                    <html lang="en">
                    <head>
                      <meta charset="UTF-8">
                      <meta http-equiv="X-UA-Compatible" content="IE=edge">
                      <meta name="viewport" content="width=device-width, initial-scale=1.0">
                      <title>Document</title>
                    </head>
                    <body>
                      <p id="message">Тут отобразится сообщение из андроидов</p>
                      <button onclick="getData()">Получить значение</button>
                      <button onclick="sendData()">Отправить значение</button>
                      <script>
                        let i = 0
                        getData = () => {
                          const data = jsInterface.getValue();
                          document.getElementById("message").innerHTML = data;
                        }

                        sendData = () => {
                          i++;
                          jsInterface.sendValue(`Hello from WebView `);
                        }
                      </script>
                    </body>
                    </html>
                    """.trimIndent(),
                    "text/html",
                    "UTF-8",
                    null
                )
            }
        }
    )
}