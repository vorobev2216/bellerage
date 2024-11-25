package com.example.test_bellerage.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.test_bellerage.network.GitHubService
import com.example.test_bellerage.screens.registration.DTO.UserLogInDTO
import com.example.test_bellerage.screens.users.DTO.UserDTORecycler
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(private val gitHubService: GitHubService) : ViewModel() {

    var users: List<UserDTORecycler>? = null
    var followers: List<UserDTORecycler>? = null
    var user: UserLogInDTO? = null


    fun getUsers(since: Int = 0, perPage: Int = 30) {
        viewModelScope.launch {
            try {
                users = gitHubService.getUsers(since, perPage)
            } catch (e: Exception) {
            }
        }
    }

    fun getFollowers(username: String) {
        viewModelScope.launch {
            try {
                followers = gitHubService.getFollowers(username)
            } catch (e: Exception) {
            }
        }
    }

    fun getUser(id: Int) {
        viewModelScope.launch {
            try {
                user = gitHubService.getUser(id)
            } catch (e: Exception) {
            }
        }
    }
}