package com.dellepiane.ceibatest.domain.model

import com.dellepiane.ceibatest.data.remote.model.UserPostResponse

data class UserPost(
    val title: String? = null,
    val body: String? = null,
)

fun UserPostResponse.toDomain(): UserPost {
    return UserPost(
        this.title,
        this.body
    )
}

