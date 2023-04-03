package com.dellepiane.ceibatest.domain.usecases

import com.dellepiane.ceibatest.domain.model.UserPost
import com.dellepiane.ceibatest.domain.repository.UserPostsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUserPostsUseCase @Inject constructor(private val userPostsRepository: UserPostsRepository) {

    fun execute(userId: Int): Flow<Result<List<UserPost>>> {
        return userPostsRepository.getUserPosts(userId)
    }
}