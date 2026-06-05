package com.example.mykmpapplication

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform