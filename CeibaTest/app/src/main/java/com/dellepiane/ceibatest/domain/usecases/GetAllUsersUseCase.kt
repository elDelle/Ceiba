package com.dellepiane.ceibatest.domain.usecases

import com.dellepiane.ceibatest.domain.model.User
import com.dellepiane.ceibatest.domain.repository.Repository
import com.dellepiane.ceibatest.resultOf
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

class GetAllUsersUseCase constructor(private val repository: Repository) {

    fun execute(): Flow<Result<List<User>>> {
        return repository.getAllUsers().map {
            resultOf { it }
        }.catch {
            emit(Result.failure(it))
        }
    }
}