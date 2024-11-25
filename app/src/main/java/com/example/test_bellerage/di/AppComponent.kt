package com.example.test_bellerage.di

import com.example.test_bellerage.MainActivity
import com.example.test_bellerage.viewmodel.MainViewModel
import dagger.Component
import dagger.Module


@Component(modules = [AppModule::class])
interface AppComponent {
    fun inject(activity: MainActivity)
    fun viewModel(): MainViewModel
}

@Module(includes = [NetworkModule::class, ViewModelModule::class])
object AppModule







