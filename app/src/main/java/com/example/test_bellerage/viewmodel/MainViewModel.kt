package com.example.test_bellerage.viewmodel

import android.util.Log
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

    private val _userDetails = MutableLiveData<UserDTORecycler>()
    val userDetails: LiveData<UserDTORecycler> get() = _userDetails

    private val _loginUser = MutableLiveData<UserLogInDTO>()
    val loginUser: LiveData<UserLogInDTO> get() = _loginUser

    fun setUserValue(newUser: UserDTORecycler) {
        _userDetails.value = newUser
    }

    fun setLoginUserValue(loginUser: UserLogInDTO) {
        _loginUser.value = loginUser
    }

    fun getUsers(since: Int = 0, perPage: Int = 30, token: String) {
        viewModelScope.launch {
            try {
                val result = withContext(Dispatchers.IO) {
                    gitHubService.getUsers(since = since, perPage = perPage, authorization ="Bearer $token"  )
                }
                _users.postValue(result)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun getFollowers(username: String, token: String) {
        viewModelScope.launch {
            try {
                val result = withContext(Dispatchers.IO) {
                    gitHubService.getFollowers(username = username, authorization = "Bearer $token")
                }
                _followersCount.postValue(result.size)
                _followers.postValue(result)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun getUser(id: String):UserLogInDTO? {
         viewModelScope.launch {
            try {
                val result = withContext(Dispatchers.IO) {
                    gitHubService.getAuthenticatedUser(authorization ="Bearer $id")
                }
                _loginUser.postValue(result)

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return _loginUser.value
    }

    val followerCounts = mutableMapOf<String, Int>()
    private val _followerCountsLiveData = MutableLiveData<Pair<String, Int>>()
    val followerCountsLiveData: LiveData<Pair<String, Int>> get() = _followerCountsLiveData

    fun getFollowersMap(username: String, token: String) {
        if (followerCounts.containsKey(username)) {
            _followerCountsLiveData.value = username to followerCounts[username]!!
            return
        }

        viewModelScope.launch {
            try {
                val followers = withContext(Dispatchers.IO) {
                    gitHubService.getFollowers(username = username, authorization = "Bearer $token")
                }
                val followerCount = followers.size
                followerCounts[username] = followerCount
                _followerCountsLiveData.postValue(username to followerCount)


            } catch (e: Exception) {
                _followerCountsLiveData.postValue(username to -1)

            }
        }
    }
}