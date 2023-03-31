package com.dellepiane.ceibatest.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.dellepiane.ceibatest.domain.model.User

@Entity
class UserEntity {
    @PrimaryKey
    var id: Int? = null

    @ColumnInfo("name")
    var name: String? = null

    @ColumnInfo("phone")
    var phone: String? = null

    @ColumnInfo("email")
    var email: String? = null
}

fun UserEntity.toDomain(): User {
    return User(
        this.id,
        this.name,
        this.phone,
        this.email
    )
}

