package com.litmus7.news.loader

import com.litmus7.common.domain.NewsResponse
import com.litmus7.common.util.Result

interface DataLoader {
    suspend fun loadFromNetwork(country: String): Result<NewsResponse>
    suspend fun loadFromLocal(): Result<NewsResponse>
}