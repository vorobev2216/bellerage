package com.example.test_bellerage.screens.registration.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.test_bellerage.screens.registration.DTO.UserLogInDTO

// TODO почему не использовал???
class RegistrationViewModel : ViewModel() {
    private val _user = MutableLiveData<UserLogInDTO?>()
    val user: LiveData<UserLogInDTO?> = _user // TODO молодец (рассуждения про сетеры и закрытие поля)

    fun setUser(user: UserLogInDTO) { // TODO это приватные методы должны быть
        _user.value = user
    }
    fun getUser(): UserLogInDTO? {
        return _user.value
    }

}