package com.dellepiane.ceibatest.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.dellepiane.ceibatest.domain.model.UserPost

@Entity
class UserPostEntity {
    @PrimaryKey
    var id: Int? = null

    @ColumnInfo("title")
    var title: String? = null

    @ColumnInfo("body")
    var body: String? = null

    @ColumnInfo("userId")
    var userId: Int? = null
}

fun UserPostEntity.toDomain(): UserPost {
    return UserPost(
        this.title,
        this.body
    )
}

