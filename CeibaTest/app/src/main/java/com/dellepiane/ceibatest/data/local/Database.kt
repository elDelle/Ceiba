package com.dellepiane.ceibatest.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

private const val DATABASE_VERSION = 1

@Database(
    entities = [
        UserEntity::class,
        UserPostEntity::class],
    version = DATABASE_VERSION
)
abstract class Database : RoomDatabase() {

    abstract fun usersDao(): UsersDao

    abstract fun userPostsDao(): UserPostsDao
}