package com.litmus7.common.domain

data class NewsResponse(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
) {
    companion object {
        const val STATUS_CACHED = "cached"
        const val STATUS_OK = "ok"
        const val STATUS_ERROR = "error"
    }
}