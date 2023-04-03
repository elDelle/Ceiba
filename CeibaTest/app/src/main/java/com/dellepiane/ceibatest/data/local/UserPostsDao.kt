package com.dellepiane.ceibatest.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface UserPostsDao {

    @Insert
    suspend fun saveUserPosts(userPosts: List<UserPostEntity>)

    @Query(value = "SELECT * FROM UserPostEntity WHERE userId = :userId")
    fun getUserPosts(userId: Int): Flow<List<UserPostEntity>>
}