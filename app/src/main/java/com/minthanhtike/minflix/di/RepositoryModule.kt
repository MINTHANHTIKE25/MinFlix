package com.minthanhtike.minflix.di

import com.minthanhtike.minflix.feature.detail.data.repo.HomeDetailRepo
import com.minthanhtike.minflix.feature.detail.data.repo.HomeDetailRepoImpl
import com.minthanhtike.minflix.feature.home.data.repo.HomeRepo
import com.minthanhtike.minflix.feature.home.data.repo.HomeRepoImpl
import com.minthanhtike.minflix.feature.home.data.repo.MockHomeRepoImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun bindHomeRepo(
        homeRepoImpl: HomeRepoImpl
    ): HomeRepo

    @Binds
    abstract fun bindHomeDetailRepo(
        homeDetailRepoImpl: HomeDetailRepoImpl
    ):HomeDetailRepo
}