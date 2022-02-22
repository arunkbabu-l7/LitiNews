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

open class NewsLoader(private val networkDataSource: NewsDataSource,
                 private val localDataSource: NewsDataSource) : DataLoader {
    private val tag = NewsLoader::class.simpleName

    override suspend fun loadFromNetwork(country: String): Result<NewsResponse> {
        Log.d(tag, "loadFromNetwork($country)")
        
        return networkDataSource.getNews(country)
            .onEach {
                Log.d(tag, "getNews($country).onEach()")
                if (it is Result.Success) {
                    Log.d(tag, "getNews($country).onEach()::Result.Success")
                    saveInCache(it.data.articles)
                }
            }.catch { e ->
                Log.d(tag, "getNews($country).catch() -- Error: $e")
                Result.Error(NewsFetchException(e.message.toString()))
                e.printStackTrace()
            }.first()
    }

    override suspend fun loadFromLocal(): Result<NewsResponse> {
        Log.d(tag, "loadFromLocal()")
        return localDataSource.getNews().first()
    }

    protected open suspend fun saveInCache(articles: List<Article>) {
        Log.d(tag, "saveInCache()  :: Size: ${articles.size}")
        localDataSource.deleteAllNews()

        articles.forEach { article ->
            localDataSource.putNews(article)
        }
    }
}