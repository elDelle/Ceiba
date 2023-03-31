package com.dellepiane.ceibatest.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dellepiane.ceibatest.domain.model.User
import com.dellepiane.ceibatest.domain.usecases.GetAllUsersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UsersViewModel @Inject constructor(
    private val getAllUsersUseCase: GetAllUsersUseCase
) : ViewModel() {

    private val _showLoading: MutableLiveData<Boolean> = MutableLiveData()
    val showLoading = _showLoading

    private val _getUsers: MutableLiveData<List<User>> = MutableLiveData()
    val getUsers = _getUsers

    private val _showFailure: MutableLiveData<String> = MutableLiveData()
    val showFailure = _showFailure

    private lateinit var usersOriginalList: List<User>

    fun getAllUsers() {
        showLoading.value = true
        viewModelScope.launch {
            delay(DELAY_TIME)
            collectAllUsers()
            showLoading.value = false
        }
    }

    private suspend fun collectAllUsers() {
        getAllUsersUseCase.execute().collect { result ->
            result.onSuccess {
                usersOriginalList = it
                _getUsers.value = usersOriginalList
            }.onFailure {
                _showFailure.value = it.message
            }
        }
    }

    fun getUsersByName(name: String) {
        val filteredList = usersOriginalList.filter {
            it.name?.lowercase()?.contains(name.lowercase()) == true
        }
        _getUsers.value = filteredList
    }

    private companion object {
        const val DELAY_TIME = 2000L
    }
}