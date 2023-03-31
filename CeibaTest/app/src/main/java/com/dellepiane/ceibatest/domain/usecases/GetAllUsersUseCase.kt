package com.dellepiane.ceibatest.domain.usecases

import com.dellepiane.ceibatest.domain.model.User
import com.dellepiane.ceibatest.domain.repository.Repository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllUsersUseCase @Inject constructor(private val repository: Repository) {

    fun execute(): Flow<Result<List<User>>> {
        return repository.getAllUsers()
    }
}