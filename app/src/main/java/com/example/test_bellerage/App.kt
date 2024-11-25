package com.example.test_bellerage

import android.app.Application
import android.content.Context
import com.example.test_bellerage.di.AppComponent
import com.example.test_bellerage.di.DaggerAppComponent

class App : Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.create()

    }

}

val Context.appComponent: AppComponent
    get() = when (this) {
        is App -> appComponent
        else -> this.applicationContext.appComponent
    }