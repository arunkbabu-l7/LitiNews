package com.litmus7.news.repository

import com.litmus7.news.domain.NewsResponse
import com.litmus7.news.util.Result

interface NewsRepository {
    suspend fun getAllNews(topic: String): Result<NewsResponse>
}