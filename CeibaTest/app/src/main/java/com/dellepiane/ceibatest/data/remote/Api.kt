package com.dellepiane.ceibatest.data.remote

import com.dellepiane.ceibatest.data.model.UserResponse
import retrofit2.http.GET

interface Api {

    @GET("users")
    suspend fun getAllUsers(): List<UserResponse>
}