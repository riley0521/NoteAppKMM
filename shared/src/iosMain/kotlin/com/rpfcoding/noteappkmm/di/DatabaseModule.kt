package com.rpfcoding.noteappkmm.di

import com.rpfcoding.noteappkmm.data.remote.HttpClientFactory
import com.rpfcoding.noteappkmm.data.repository.AccountRepositoryImpl
import com.rpfcoding.noteappkmm.domain.repository.AccountRepository

/**
 * This is called from Xcode so the compiler will think that this class is unused.
 */
@Suppress("unused")
class DatabaseModule {

    val accountRepository: AccountRepository by lazy {
        AccountRepositoryImpl(
            HttpClientFactory().create()
        )
    }
}