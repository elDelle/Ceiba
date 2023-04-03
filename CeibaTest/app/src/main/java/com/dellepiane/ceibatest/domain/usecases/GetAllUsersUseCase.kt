package com.dellepiane.ceibatest.domain.usecases

import com.dellepiane.ceibatest.domain.model.User
import com.dellepiane.ceibatest.domain.repository.UsersRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllUsersUseCase @Inject constructor(private val usersRepository: UsersRepository) {

    fun execute(): Flow<Result<List<User>>> {
        return usersRepository.getAllUsers()
    }
}