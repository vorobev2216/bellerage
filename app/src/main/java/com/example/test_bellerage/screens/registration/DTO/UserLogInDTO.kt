package com.example.test_bellerage.screens.registration.DTO

data class UserLogInDTO(
    val login: String,
    val id: Int,
    val avatar_url: String,
    val url: String,
    val name: String?,
    val company: String?,
    val blog: String?,
    val location: String?,
    val email: String?,
    val bio: String?,
    val twitter_username: String?,
    val public_repos: Int,
    val followers: Int,
    val following: Int
)