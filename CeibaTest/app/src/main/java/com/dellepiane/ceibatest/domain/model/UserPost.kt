package com.dellepiane.ceibatest.domain.model

import com.dellepiane.ceibatest.data.local.UserPostEntity
import com.dellepiane.ceibatest.data.remote.model.UserPostResponse

data class UserPost(
    val id: Int? = null,
    val title: String? = null,
    val body: String? = null,
    val userId: Int? = null
)

fun UserPostResponse.toEntity(): UserPostEntity {
    return UserPostEntity().apply {
        id = this@toEntity.id
        title = this@toEntity.title
        body = this@toEntity.body
        userId = this@toEntity.userId
    }
}

