package com.litmus7.news.util

fun String.toCleanDate(): String {
    val lastIndex = this.lastIndexOf("T", ignoreCase = true)
    this.trim()
    return this.substring(0, lastIndex)
}