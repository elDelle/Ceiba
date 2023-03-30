package com.dellepiane.ceibatest.data.repository

import com.dellepiane.ceibatest.data.remote.Api
import com.dellepiane.ceibatest.domain.model.User
import com.dellepiane.ceibatest.domain.model.toDomain
import com.dellepiane.ceibatest.domain.repository.Repository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RepositoryImpl @Inject constructor(private val api: Api) : Repository {
    override fun getAllUsers(): Flow<List<User>> {
        return flow {
            emit(getAllUsersApi())
        }
    }

    private suspend fun getAllUsersApi() = api.getAllUsers().map {
        it.toDomain()
    }
}
