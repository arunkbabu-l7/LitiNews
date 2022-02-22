package com.litmus7.news.loader

import android.util.Log
import com.litmus7.common.domain.Article
import com.litmus7.common.domain.NewsResponse
import com.litmus7.common.exception.NewsFetchException
import com.litmus7.common.util.Result
import com.litmus7.news.datasource.NewsDataSource
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.onEach

class NewsLoader(private val networkDataSource: NewsDataSource,
                 private val localDataSource: NewsDataSource)
{
    private val tag = NewsLoader::class.simpleName

    suspend fun loadFromNetwork(country: String): Result<NewsResponse> {
        return networkDataSource.getNews(country)
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

    suspend fun loadFromLocal(): Result<NewsResponse> = localDataSource.getNews().first()

    private suspend fun saveInCache(articles: List<Article>) {
        Log.d(tag, "saveInCache()  :: Size: ${articles.size}")
        localDataSource.deleteAllNews()

        articles.forEach { article ->
            localDataSource.putNews(article)
        }
    }
}