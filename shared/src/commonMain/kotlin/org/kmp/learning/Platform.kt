package org.kmp.learning

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform