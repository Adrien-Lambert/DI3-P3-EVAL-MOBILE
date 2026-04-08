package com.example.evalp3

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform