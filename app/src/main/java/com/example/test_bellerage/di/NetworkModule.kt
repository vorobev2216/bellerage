package com.example.test_bellerage.di

import com.example.test_bellerage.network.GitHubService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.create

@Module
class NetworkModule {

    @Provides
    fun provideUsersService(): GitHubService {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .build()
        return retrofit.create()
    }
}