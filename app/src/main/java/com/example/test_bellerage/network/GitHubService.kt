package com.example.test_bellerage.network

import com.example.test_bellerage.screens.users.DTO.User
import com.example.test_bellerage.screens.registration.DTO.UserLogInDTO
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GitHubService {
    @GET("/users")
    suspend fun getUsers(@Query("since") since: Int, @Query("per_page") perPage: Int = 30):  List<User>

}

interface GitHubApiService {
    @GET("user/{id}")
    suspend fun getUser(@Path("id") id: Int): UserLogInDTO
}