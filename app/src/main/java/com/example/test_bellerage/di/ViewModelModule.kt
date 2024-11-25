package com.example.test_bellerage.di

import com.example.test_bellerage.network.GitHubService
import com.example.test_bellerage.viewmodel.MainViewModel
import dagger.Module
import dagger.Provides

@Module
class ViewModelModule {
    @Provides

    fun provideMainViewModel(gitHubService: GitHubService): MainViewModel {
        return MainViewModel(gitHubService)
    }
}