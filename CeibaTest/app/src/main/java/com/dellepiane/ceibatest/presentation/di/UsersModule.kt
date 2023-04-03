package com.dellepiane.ceibatest.presentation.di

import com.dellepiane.ceibatest.data.remote.Api
import com.dellepiane.ceibatest.data.repository.UserPostsRepositoryImpl
import com.dellepiane.ceibatest.data.repository.UsersRepositoryImpl
import com.dellepiane.ceibatest.domain.repository.UserPostsRepository
import com.dellepiane.ceibatest.domain.repository.UsersRepository
import com.dellepiane.ceibatest.domain.usecases.GetUserPostsUseCase
import com.dellepiane.ceibatest.domain.usecases.GetAllUsersUseCase
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module(includes = [UsersModule.BindModules::class])
@InstallIn(SingletonComponent::class)
object UsersModule {

    @Provides
    @Singleton
    fun provideApi(retrofit: Retrofit): Api {
        return retrofit.create(Api::class.java)
    }

    @Provides
    fun provideGetAllUsersUseCase(usersRepository: UsersRepository): GetAllUsersUseCase {
        return GetAllUsersUseCase(usersRepository)
    }

    @Provides
    fun provideGetAllUserPostsUseCase(userPostsRepository: UserPostsRepository): GetUserPostsUseCase {
        return GetUserPostsUseCase(userPostsRepository)
    }

    @Module
    @InstallIn(SingletonComponent::class)
    interface BindModules {
        @Binds
        @Singleton
        fun bindUsersRepository(usersRepositoryImpl: UsersRepositoryImpl): UsersRepository

        @Binds
        @Singleton
        fun bindUserPostsRepository(userPostsRepositoryImpl: UserPostsRepositoryImpl): UserPostsRepository
    }
}