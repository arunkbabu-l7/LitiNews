package com.litmus7.common.util

import com.litmus7.common.domain.Article
import com.litmus7.common.domain.NewsResponse

fun String.toCleanDate(): String {
    val lastIndex = this.lastIndexOf("T", ignoreCase = true)
    this.trim()
    return this.substring(0, lastIndex)
}

fun List<Article>.toCachedNewsResponse(): NewsResponse {
    return NewsResponse(articles = this, NewsResponse.STATUS_CACHED, this.size)
}