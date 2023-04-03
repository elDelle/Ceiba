package com.dellepiane.ceibatest.domain.repository

import com.dellepiane.ceibatest.domain.model.User
import kotlinx.coroutines.flow.Flow

interface UsersRepository {
    fun getAllUsers(): Flow<Result<List<User>>>
}