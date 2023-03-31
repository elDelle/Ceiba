package com.dellepiane.ceibatest.domain.repository

import com.dellepiane.ceibatest.domain.model.User
import com.dellepiane.ceibatest.domain.model.UserPost
import kotlinx.coroutines.flow.Flow

interface Repository {
    fun getAllUsers(): Flow<Result<List<User>>>
    fun getAllUserPosts(userId: Int): Flow<Result<List<UserPost>>>
    suspend fun refreshUsers()
}