package com.litmus7.common.util

import com.litmus7.common.domain.Article
import com.litmus7.common.domain.NewsResponse

fun String.toCleanDate(): String {
    val lastIndex = this.lastIndexOf("T", ignoreCase = true)
    this.trim()
    return this.substring(0, lastIndex)
}

fun List<Article>.toCachedNewsResponse() =
    NewsResponse(articles = this, status = NewsResponse.STATUS_CACHED, totalResults = this.size)