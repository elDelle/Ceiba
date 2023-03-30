package com.dellepiane.ceibatest.domain.model

import com.dellepiane.ceibatest.data.UserResponse

data class User(
    val id: Int? = null,
    val name: String? = null,
    val phone: String? = null,
    val email: String? = null
)

fun UserResponse.toDomain(): User {
    return User(
        this.id,
        this.name,
        this.phone,
        this.email
    )
}

