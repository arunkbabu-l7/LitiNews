package com.litmus7.news.repository

import android.util.Log
import com.litmus7.news.domain.NewsResponse
import com.litmus7.news.exception.NewsFetchException
import com.litmus7.news.network.HeadlinesDataSource
import com.litmus7.news.util.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import javax.inject.Inject

class HeadlinesRepository @Inject constructor(private val newsDataSource: HeadlinesDataSource) {
    private val tag = HeadlinesRepository::class.simpleName

    suspend fun getTopHeadlines(country: String): Flow<Result<NewsResponse>> {
        Log.d(tag, "getTopHeadlines()")
        val news = newsDataSource.getTopHeadlines(country)
        news.catch { e ->
            Result.Error(NewsFetchException(e.message.toString()))
        }
        return news
    }
}