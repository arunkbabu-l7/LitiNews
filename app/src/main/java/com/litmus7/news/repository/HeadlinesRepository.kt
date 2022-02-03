package com.litmus7.news.repository

import android.util.Log
import com.litmus7.news.database.NewsDbDataSource
import com.litmus7.news.domain.Article
import com.litmus7.news.domain.NewsResponse
import com.litmus7.news.exception.NewsFetchException
import com.litmus7.news.network.HeadlinesDataSource
import com.litmus7.news.util.NetworkUtils
import com.litmus7.news.util.Result
import com.litmus7.news.util.toCachedNewsResponse
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class HeadlinesRepository @Inject constructor(
    private val newsDataSource: HeadlinesDataSource,
    private val newsDbDataSource: NewsDbDataSource
) {
    private val tag = HeadlinesRepository::class.simpleName

    suspend fun getTopHeadlines(country: String): Flow<Result<NewsResponse>> = flow {
        Log.d(tag, "getTopHeadlines()")

        if (NetworkUtils.isInternetConnected) {
            emit(loadFromNetwork(country))
        } else {
            emit(loadFromLocal())
        }
    }

    private suspend fun loadFromNetwork(country: String): Result<NewsResponse> {
        return newsDataSource.getTopHeadlines(country)
            .onEach {
                Log.d(tag, "news.onEach()")
                if (it is Result.Success) {
                    Log.d(tag, "news.onEach()::Result.Success")
                    saveInCache(it.data.articles)
                }

            }.catch { e ->
                Log.d(tag, ".catch() -- Error: $e")
                Result.Error(NewsFetchException(e.message.toString()))
                e.printStackTrace()
            }.first()
    }

    private suspend fun loadFromLocal(): Result<NewsResponse> {
        return newsDbDataSource.allArticles.map { articles ->
            Log.d(tag, "Db Data Loaded")
            if (articles.isNotEmpty()) {
                Result.Success(articles.toCachedNewsResponse())
            } else {
                Result.Error(NewsFetchException("Database is empty"))
            }
        }.first()
    }

    private suspend fun saveInCache(articles: List<Article>) {
        Log.d(tag, "saveInCache()  :: Size: ${articles.size}")
        newsDbDataSource.deleteAllArticles()

        articles.forEach { article ->
            newsDbDataSource.insertArticle(article)
        }
    }
}