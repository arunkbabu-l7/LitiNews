package com.litmus7.news.domain

data class NewsResponse(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)