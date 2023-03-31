package com.dellepiane.ceibatest.domain.usecases

import com.dellepiane.ceibatest.domain.model.User
import com.dellepiane.ceibatest.domain.model.UserPost
import com.dellepiane.ceibatest.domain.repository.Repository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllUserPostsUseCase @Inject constructor(private val repository: Repository) {

    fun execute(userId: Int): Flow<Result<List<UserPost>>> {
        return repository.getAllUserPosts(userId)
    }
}