package com.dellepiane.ceibatest.data

interface Api {

    suspend fun getAllUsers(): List<UserResponse>
}