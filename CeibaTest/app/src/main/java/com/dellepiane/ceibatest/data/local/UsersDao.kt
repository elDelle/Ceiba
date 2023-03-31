package com.dellepiane.ceibatest.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface UsersDao {

    @Insert
    suspend fun saveUsers(users: List<UserEntity>)

    @Query(value = "SELECT * FROM UserEntity")
    fun getUsers(): Flow<List<UserEntity>>
}