package com.litmus7.news.repository

import android.util.Log
import com.litmus7.news.database.NewsDbDataSource
import com.litmus7.news.domain.Article
import com.litmus7.news.domain.NewsResponse
import com.litmus7.news.exception.NewsFetchException
import com.litmus7.news.network.HeadlinesDataSource
import com.litmus7.news.util.Result
import com.litmus7.news.util.toCachedNewsResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class HeadlinesRepository @Inject constructor(
    private val newsDataSource: HeadlinesDataSource,
    private val newsDbDataSource: NewsDbDataSource
) {
    private val tag = HeadlinesRepository::class.simpleName

    suspend fun getTopHeadlines(country: String): Flow<Result<NewsResponse>> = flow {
        Log.d(tag, "getTopHeadlines()")

        newsDbDataSource.allArticles.collect { articles ->
            emit(Result.Success(articles.toCachedNewsResponse()))
        }

        val news = newsDataSource.getTopHeadlines(country)
        news.onEach {
            emit(it)
            if (it is Result.Success) {
                saveInCache(it.data.articles)
            }
        }.catch { e ->
            emit(Result.Error(NewsFetchException(e.message.toString())))
        }
    }

    private suspend fun saveInCache(articles: List<Article>) {
        articles.forEach { article ->
            newsDbDataSource.insertArticle(article)
        }
    }
}