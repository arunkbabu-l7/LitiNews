package com.litmus7.news.util

import com.litmus7.news.domain.Article
import com.litmus7.news.domain.NewsResponse

fun String.toCleanDate(): String {
    val lastIndex = this.lastIndexOf("T", ignoreCase = true)
    this.trim()
    return this.substring(0, lastIndex)
}

fun List<Article>.toCachedNewsResponse(): NewsResponse {
    return NewsResponse(articles = this, NewsResponse.STATUS_CACHED, this.size)
}