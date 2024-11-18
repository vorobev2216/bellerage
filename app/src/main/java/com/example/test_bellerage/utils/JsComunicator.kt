package com.example.test_bellerage.utils

import android.content.Context
import android.webkit.JavascriptInterface
import android.widget.Toast

class MyJavaScriptInterface(private val context: Context) {

    private var count = 0

    @JavascriptInterface
    fun getValue(): String {
        count++
        return "Hello from android $count"
    }

    @JavascriptInterface
    fun sendValue(str: String) {
        Toast.makeText(context, str, Toast.LENGTH_SHORT).show()
    }
}
