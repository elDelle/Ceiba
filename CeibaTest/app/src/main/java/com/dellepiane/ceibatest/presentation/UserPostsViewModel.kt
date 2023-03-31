package com.dellepiane.ceibatest.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dellepiane.ceibatest.domain.model.UserPost
import com.dellepiane.ceibatest.domain.usecases.GetAllUserPostsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserPostsViewModel @Inject constructor(
    private val getAllUserPostsUseCase: GetAllUserPostsUseCase
) : ViewModel() {

    private val _showLoading: MutableLiveData<Boolean> = MutableLiveData()
    val showLoading = _showLoading

    private val _getUserPosts: MutableLiveData<List<UserPost>> = MutableLiveData()
    val getUserPosts = _getUserPosts

    private val _showFailure: MutableLiveData<String> = MutableLiveData()
    val showFailure = _showFailure

    fun getAllUserPosts(idUser: Int) {
        showLoading.value = true
        viewModelScope.launch {
            delay(DELAY_TIME)
            collectAllUserPosts(idUser)
            showLoading.value = false
        }
    }

    private suspend fun collectAllUserPosts(idUser: Int) {
        getAllUserPostsUseCase.execute(idUser).collect { result ->
            result.onSuccess {
                _getUserPosts.value = it
            }.onFailure {
                _showFailure.value = it.message
            }
        }
    }

    private companion object {
        const val DELAY_TIME = 2000L
    }
}