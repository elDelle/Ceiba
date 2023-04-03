package com.dellepiane.ceibatest.domain.repository

import com.dellepiane.ceibatest.domain.model.UserPost
import kotlinx.coroutines.flow.Flow

interface UserPostsRepository {
    fun getUserPosts(userId: Int): Flow<Result<List<UserPost>>>
}