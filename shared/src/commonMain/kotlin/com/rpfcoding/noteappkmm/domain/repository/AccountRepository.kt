package com.rpfcoding.noteappkmm.domain.repository

interface AccountRepository {

    @Throws(Exception::class)
    suspend fun returnSomeString(): String
}