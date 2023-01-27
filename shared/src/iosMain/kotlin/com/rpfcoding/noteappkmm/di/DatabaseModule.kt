package com.rpfcoding.noteappkmm.di

import co.touchlab.stately.freeze
import com.rpfcoding.noteappkmm.data.remote.HttpClientFactory
import com.rpfcoding.noteappkmm.data.repository.AccountRepositoryImpl
import com.rpfcoding.noteappkmm.domain.repository.AccountRepository

class DatabaseModule {

    val accountRepository: AccountRepository by lazy {
        AccountRepositoryImpl(
            HttpClientFactory().create()
        )
    }
}