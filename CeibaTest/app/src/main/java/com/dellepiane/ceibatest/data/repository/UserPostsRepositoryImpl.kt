package com.dellepiane.ceibatest.data.repository

import com.dellepiane.ceibatest.data.local.UserPostEntity
import com.dellepiane.ceibatest.data.local.UserPostsDao
import com.dellepiane.ceibatest.data.local.toDomain
import com.dellepiane.ceibatest.data.remote.Api
import com.dellepiane.ceibatest.domain.model.UserPost
import com.dellepiane.ceibatest.domain.model.toEntity
import com.dellepiane.ceibatest.domain.repository.UserPostsRepository
import com.dellepiane.ceibatest.resultOf
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import java.io.IOException
import javax.inject.Inject

class UserPostsRepositoryImpl @Inject constructor(
    private val remoteSource: Api,
    private val localSource: UserPostsDao
) : UserPostsRepository {

    override fun getUserPosts(userId: Int): Flow<Result<List<UserPost>>> {
        return localSource.getUserPosts(userId).map { listUserPostsLocal ->
            transformLocalUserPostsFromDomain(listUserPostsLocal)
        }.onEach { listUserPostsLocal ->
            try {
                listUserPostsLocal.map {
                    val userPost = it.map { userPost ->
                        userPost.id
                    }
                    val userPostsListFromRemoteToLocal = transformUserPostsApi(userId)
                    val userPostsFromEntity = userPostsListFromRemoteToLocal.map { userPostEntity ->
                        userPostEntity.id
                    }
                    if (userPost != userPostsFromEntity) {
                        saveUserPostsInLocal(userPostsListFromRemoteToLocal)
                    }
                }
            } catch (e: java.lang.Exception) {
                localSource.getUserPosts(userId)
            }
        }.catch {
            if (it is IOException) {
                emit(Result.failure(Throwable(INTERNET_CONNECTION_ERROR)))
            } else {
                emit(Result.failure(Throwable(OTHER_ERROR)))
            }
        }
    }

    private fun transformLocalUserPostsFromDomain(listUserPostsLocal: List<UserPostEntity>): Result<List<UserPost>> {
        return resultOf {
            listUserPostsLocal.map { cached ->
                cached.toDomain()
            }
        }
    }

    private suspend fun transformUserPostsApi(userId: Int) =
        remoteSource.getAllUserPosts(userId).map {
            it.toEntity()
        }

    private suspend fun saveUserPostsInLocal(userPostsListRemote: List<UserPostEntity>) {
        localSource.saveUserPosts(userPostsListRemote.map {
            UserPostEntity().apply {
                id = it.id
                title = it.title
                body = it.body
                userId = it.userId
            }
        })
    }

    private companion object {
        const val INTERNET_CONNECTION_ERROR = "Revisa tu conexión a internet"
        const val OTHER_ERROR = "Ocurrió un error inesperado"
    }
}
