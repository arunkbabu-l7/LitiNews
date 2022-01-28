package com.litmus7.news.repository

import com.litmus7.news.domain.NewsResponse
import com.litmus7.news.util.Result

interface NewsRepository {
    suspend fun getNewsByTopic(topic: String): Result<NewsResponse>
    suspend fun getTopHeadlines(country: String): Result<NewsResponse>
}