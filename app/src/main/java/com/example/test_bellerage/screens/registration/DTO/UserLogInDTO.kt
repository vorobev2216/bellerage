package com.example.test_bellerage.screens.registration.DTO

import kotlinx.serialization.Serializable

@Serializable
data class UserLogInDTO(
    val login: String?,
    val id: Int?,
    val avatar_url: String,
    val url: String,
    val name: String? = null,
    val company: String? = null,
    val blog: String? = null,
    val location: String? = null,
    val email: String? = null,
    val bio: String? = null,
    val twitter_username: String? = null,
    val public_repos: Int?,
    val followers: Int?,
    val following: Int?
)