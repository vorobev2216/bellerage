package com.example.test_bellerage.di

import com.example.test_bellerage.network.GitHubService
import dagger.Component
import dagger.Module


@Component(modules = [AppModule::class])
interface AppComponent {
    fun mainViewModelFactory(): MainViewModelFactory
    fun gitHubService(): GitHubService
}

@Module(includes = [NetworkModule::class, ViewModelModule::class])
object AppModule







