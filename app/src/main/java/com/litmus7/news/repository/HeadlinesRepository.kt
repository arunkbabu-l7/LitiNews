package com.litmus7.news.repository

import android.util.Log
import com.litmus7.common.domain.NewsResponse
import com.litmus7.common.util.NetworkUtils
import com.litmus7.common.util.Result
import com.litmus7.news.datasource.NewsDataSource
import com.litmus7.news.loader.NewsLoader
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class HeadlinesRepository @Inject constructor(
    private val headlinesDataSource: NewsDataSource,
    private val newsDbDataSource: NewsDataSource
) : NewsRepository {
    private val tag = HeadlinesRepository::class.simpleName

    override fun fetchNews(country: String): Flow<Result<NewsResponse>> = flow {
        Log.d(tag, "HeadlinesRepository#fetchNews()")

        val newsLoader = NewsLoader(headlinesDataSource, newsDbDataSource)
        if (NetworkUtils.isInternetConnected) {
            emit(newsLoader.loadFromNetwork(country))
        } else {
            emit(newsLoader.loadFromLocal())
        }
    }
}