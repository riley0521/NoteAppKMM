package com.rpfcoding.noteappkmm.android.di

import com.rpfcoding.noteappkmm.data.repository.AccountRepositoryImpl
import com.rpfcoding.noteappkmm.domain.repository.AccountRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindAccountRepository(impl: AccountRepositoryImpl): AccountRepository
}