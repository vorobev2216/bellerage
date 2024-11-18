package com.example.test_bellerage.screens.registration.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.test_bellerage.screens.registration.DTO.UserLogInDTO

class RegistrationViewModel : ViewModel() {
    private val _user = MutableLiveData<UserLogInDTO?>()
    val user: LiveData<UserLogInDTO?> = _user

    fun setUser(user: UserLogInDTO) {
        _user.value = user
    }
    fun getUser(): UserLogInDTO? {
        return _user.value
    }

}