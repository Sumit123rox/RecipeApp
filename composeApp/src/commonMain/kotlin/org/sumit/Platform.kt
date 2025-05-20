package org.sumit

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform