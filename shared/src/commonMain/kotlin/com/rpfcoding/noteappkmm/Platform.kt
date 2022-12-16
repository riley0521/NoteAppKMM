package com.rpfcoding.noteappkmm

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform