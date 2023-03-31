package com.dellepiane.ceibatest.data.remote

import com.dellepiane.ceibatest.data.remote.model.UserPostResponse
import com.dellepiane.ceibatest.data.remote.model.UserResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {

    @GET("users")
    suspend fun getAllUsers(): List<UserResponse>

    @GET("posts")
    suspend fun getAllUserPosts(@Query("userId") userId: Int): List<UserPostResponse>
}