package com.litmus7.news.util

sealed class Result<out R> {
    class Success<out T>(val data: T): Result<T>()
    class Error(val exception: Exception): Result<Nothing>()
}
