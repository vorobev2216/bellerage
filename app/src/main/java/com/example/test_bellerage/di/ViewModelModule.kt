package com.example.test_bellerage.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.test_bellerage.network.GitHubService
import com.example.test_bellerage.viewmodel.MainViewModel
import dagger.Module
import dagger.Provides
import javax.inject.Inject
import javax.inject.Singleton

@Module
class ViewModelModule {
    @Provides
    fun provideMainViewModelFactory(gitHubService: GitHubService): MainViewModelFactory {
        return MainViewModelFactory(gitHubService)
    }

    @Provides
    fun provideMainViewModel(factory: MainViewModelFactory): MainViewModel {
        return factory.create(MainViewModel::class.java)
    }

}

class MainViewModelFactory @Inject constructor(private val gitHubService: GitHubService) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(gitHubService) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}