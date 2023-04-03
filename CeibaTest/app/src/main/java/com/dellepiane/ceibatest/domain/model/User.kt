package com.dellepiane.ceibatest.domain.model

import com.dellepiane.ceibatest.data.local.UserEntity
import com.dellepiane.ceibatest.data.remote.model.UserResponse

data class User(
    val id: Int? = null,
    val name: String? = null,
    val phone: String? = null,
    val email: String? = null
)

fun UserResponse.toEntity(): UserEntity {
    return UserEntity().apply {
        id = this@toEntity.id
        name = this@toEntity.name
        phone = this@toEntity.phone
        email = this@toEntity.email
    }
}

