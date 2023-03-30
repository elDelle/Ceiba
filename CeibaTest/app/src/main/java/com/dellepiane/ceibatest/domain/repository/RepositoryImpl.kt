package com.dellepiane.ceibatest.domain.repository

import com.dellepiane.ceibatest.data.Api
import com.dellepiane.ceibatest.domain.model.User
import com.dellepiane.ceibatest.domain.model.toDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class RepositoryImpl(private val api: Api) : Repository {

    override fun getAllUsers(): Flow<List<User>> {
        return flow {
            emit(getAllUsersApi())
        }
    }

    private suspend fun getAllUsersApi() = api.getAllUsers().map {
        it.toDomain()
    }
}
