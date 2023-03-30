package com.dellepiane.ceibatest.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dellepiane.ceibatest.domain.usecases.GetAllUsersUseCase
import com.dellepiane.ceibatest.domain.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UsersViewModel @Inject constructor(private val getAllUsersUseCase: GetAllUsersUseCase) : ViewModel() {

    private var _showLoading: MutableLiveData<Boolean> = MutableLiveData()
    var showLoading = _showLoading

    private var _getUsers: MutableLiveData<List<User>> = MutableLiveData()
    var getUsers = _getUsers

    fun getAllUsers() {
        viewModelScope.launch {
            collectAllUsers().collect()
        }
    }

    private fun collectAllUsers(): Flow<Result<List<User>>> {
        return flow {
            getAllUsersUseCase.execute().collect { result ->
                result.onSuccess {
                    _getUsers.value = it
                }.onFailure {
                    it
                }
            }
        }
    }
}