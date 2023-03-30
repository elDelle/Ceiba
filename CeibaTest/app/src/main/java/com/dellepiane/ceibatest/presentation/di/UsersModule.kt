package com.dellepiane.ceibatest.presentation.di

import com.dellepiane.ceibatest.data.remote.Api
import com.dellepiane.ceibatest.data.repository.RepositoryImpl
import com.dellepiane.ceibatest.domain.repository.Repository
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
    fun provideGetAllUsersUseCase(repository: Repository): GetAllUsersUseCase {
        return GetAllUsersUseCase(repository)
    }

    @Module
    @InstallIn(SingletonComponent::class)
    interface BindModules {
        @Binds
        @Singleton
        fun bindRepository(repositoryImpl: RepositoryImpl): Repository
    }
}