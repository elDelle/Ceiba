package com.dellepiane.ceibatest.data.remote.di

import android.content.Context
import androidx.room.Room
import com.dellepiane.ceibatest.data.local.Database
import com.dellepiane.ceibatest.data.local.UserPostsDao
import com.dellepiane.ceibatest.data.local.UsersDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

private const val DATABASE_NAME = "ceibaDB"

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): Database {
        return Room.databaseBuilder(
            context,
            Database::class.java,
            DATABASE_NAME
        ).build()
    }

    @Singleton
    @Provides
    fun provideUsersDao(database: Database): UsersDao {
        return database.usersDao()
    }

    @Singleton
    @Provides
    fun provideUserPostsDao(database: Database): UserPostsDao {
        return database.userPostsDao()
    }
}