package com.example.test_bellerage.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.test_bellerage.network.GitHubService
import com.example.test_bellerage.screens.registration.DTO.UserLogInDTO
import com.example.test_bellerage.screens.users.DTO.UserDTORecycler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MainViewModel @Inject constructor(private val gitHubService: GitHubService) : ViewModel() {

    private val _users = MutableLiveData<List<UserDTORecycler>>()
    val users: LiveData<List<UserDTORecycler>> get() = _users

    private val _followers = MutableLiveData<List<UserDTORecycler>>()
    val followers: LiveData<List<UserDTORecycler>> get() = _followers

    private val _followersCount = MutableLiveData<Int>()
    val followersCount: LiveData<Int> get() = _followersCount

    private val _user = MutableLiveData<UserLogInDTO>()
    val user: LiveData<UserLogInDTO> get() = _user

    fun getUsers(since: Int = 0, perPage: Int = 30) {
        viewModelScope.launch {
            try {
                val result = withContext(Dispatchers.IO) {
                    gitHubService.getUsers(since, perPage)
                }
                _users.postValue(result)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun getFollowers(username: String) {
        viewModelScope.launch {
            try {
                val result = withContext(Dispatchers.IO) {
                    gitHubService.getFollowers(username)
                }
                _followers.postValue(result)
                _followersCount.postValue(result.size)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun getUser(id: Int) {
        viewModelScope.launch {
            try {
                val result = withContext(Dispatchers.IO) {
                    gitHubService.getUser(id)
                }
                _user.postValue(result)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}