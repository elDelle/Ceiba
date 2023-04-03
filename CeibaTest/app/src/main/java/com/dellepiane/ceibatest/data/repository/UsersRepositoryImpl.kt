package com.dellepiane.ceibatest.data.repository

import com.dellepiane.ceibatest.data.local.UserEntity
import com.dellepiane.ceibatest.data.local.UsersDao
import com.dellepiane.ceibatest.data.local.toDomain
import com.dellepiane.ceibatest.data.remote.Api
import com.dellepiane.ceibatest.domain.model.User
import com.dellepiane.ceibatest.domain.model.toEntity
import com.dellepiane.ceibatest.domain.repository.UsersRepository
import com.dellepiane.ceibatest.resultOf
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import java.io.IOException
import javax.inject.Inject

class UsersRepositoryImpl @Inject constructor(
    private val remoteSource: Api,
    private val localSource: UsersDao
) : UsersRepository {

    override fun getAllUsers(): Flow<Result<List<User>>> {
        return localSource.getUsers().map { listUserLocal ->
            transformLocalUsersFromDomain(listUserLocal)
        }.onEach { listUserLocal ->
            listUserLocal.map {
                try {
                    val usersListFromRemoteToLocal = transformAllUsersFromApiToLocal()
                    val user = it.map { user ->
                        user.id
                    }
                    val usersFromEntity = usersListFromRemoteToLocal.map { userEntity ->
                        userEntity.id
                    }
                    if (user != usersFromEntity) {
                        saveUsersInLocal(usersListFromRemoteToLocal)
                    } else {
                        //TODO
                    }
                } catch (e: java.lang.Exception){
                    localSource.getUsers()
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

    private suspend fun saveUsersInLocal(userListRemote: List<UserEntity>) {
        localSource.saveUsers(userListRemote.map {
            UserEntity().apply {
                id = it.id
                name = it.name
                phone = it.phone
                email = it.email
            }
        })
    }

    private fun transformLocalUsersFromDomain(listUsersLocal: List<UserEntity>): Result<List<User>> {
        return resultOf {
            listUsersLocal.map { cached ->
                cached.toDomain()
            }
        }
    }

    private suspend fun transformAllUsersFromApiToLocal() = remoteSource.getAllUsers().map {
        it.toEntity()
    }

    private companion object {
        const val INTERNET_CONNECTION_ERROR = "Revisa tu conexión a internet"
        const val OTHER_ERROR = "Ocurrió un error inesperado"
    }
}
