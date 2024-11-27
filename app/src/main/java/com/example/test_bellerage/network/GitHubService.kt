package com.example.test_bellerage.network

import com.example.test_bellerage.screens.users.DTO.UserDTORecycler
import com.example.test_bellerage.screens.registration.DTO.UserLogInDTO
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface GitHubService {
    @GET("/users")
    suspend fun getUsers(
        @Header("Accept") accept: String = "application/vnd.github+json",
        @Header("Authorization") authorization: String,
        @Header("X-GitHub-Api-Version") apiVersion: String = "2022-11-28",
        @Query("since") since: Int,
        @Query("per_page") perPage: Int = 100
    ): List<UserDTORecycler>

    @GET("users/{username}/followers")
    suspend fun getFollowers(
        @Header("Accept") accept: String = "application/vnd.github+json",
        @Header("Authorization") authorization: String,
        @Header("X-GitHub-Api-Version") apiVersion: String = "2022-11-28",
        @Path("username") username: String
    ): List<UserDTORecycler>


    @GET("/user")
    suspend fun getAuthenticatedUser(
        @Header("Accept") accept: String = "application/vnd.github+json",
        @Header("Authorization") authorization: String,
        @Header("X-GitHub-Api-Version") apiVersion: String = "2022-11-28"
    ): UserLogInDTO
}

