package com.dellepiane.ceibatest.data.repository

import com.dellepiane.ceibatest.data.local.UserEntity
import com.dellepiane.ceibatest.data.local.UsersDao
import com.dellepiane.ceibatest.data.local.toDomain
import com.dellepiane.ceibatest.data.remote.Api
import com.dellepiane.ceibatest.domain.model.User
import com.dellepiane.ceibatest.domain.model.UserPost
import com.dellepiane.ceibatest.domain.model.toDomain
import com.dellepiane.ceibatest.domain.repository.Repository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import java.io.IOException
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val remoteSource: Api,
    private val localSource: UsersDao
) : Repository {

    override fun getAllUsers(): Flow<Result<List<User>>> {
        return flow {
            emit(Result.success(getAllUsersApi()))
        }.onStart {
            localSource.getUsers().onEach {
                if (it.isEmpty()) {
                    refreshUsers()
                } else {
                    it.map { cached ->
                        cached.toDomain()
                    }
                }
            }
        }.catch {
            if (it is IOException) {
                emit(Result.failure(Throwable(INTERNET_CONNECTION_ERROR)))
            } else {
                emit(Result.failure(Throwable(OTHER_ERROR)))
            }
        }
    }

    private suspend fun getAllUsersApi() = remoteSource.getAllUsers().map {
        it.toDomain()
    }

    override suspend fun refreshUsers() {
        remoteSource
            .getAllUsers()
            .map {
                it.toDomain()
            }
            .also {
                localSource.saveUsers(it.map {
                    UserEntity().apply {
                        id = it.id
                        name = it.name
                        phone = it.phone
                        email = it.email
                    }
                })
            }
    }

    override fun getAllUserPosts(userId: Int): Flow<Result<List<UserPost>>> {
        return flow {
            emit(Result.success(getAllUserPostsApi(userId)))
        }.onStart {
            localSource.getUsers().onEach {
                if (it.isEmpty()) {
                    refreshUsers()
                } else {
                    it.map { cached ->
                        cached.toDomain()
                    }
                }
            }
        }.catch {
            if (it is IOException) {
                emit(Result.failure(Throwable(INTERNET_CONNECTION_ERROR)))
            } else {
                emit(Result.failure(Throwable(OTHER_ERROR)))
            }
        }
    }

    private suspend fun getAllUserPostsApi(userId: Int) = remoteSource.getAllUserPosts(userId).map {
        it.toDomain()
    }

    private companion object {
        const val INTERNET_CONNECTION_ERROR = "Revisa tu conexión a internet"
        const val OTHER_ERROR = "Ocurrió un error inesperado"
    }
}
