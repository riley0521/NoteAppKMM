package com.rpfcoding.noteappkmm.data.remote

import io.ktor.client.*

expect class HttpClientFactory {

    fun create(): HttpClient
}