package com.litmus7.news.datasource

import android.util.Log
import com.litmus7.common.domain.Article
import com.litmus7.common.domain.NewsResponse
import com.litmus7.common.exception.NewsFetchException
import com.litmus7.common.util.Result
import com.litmus7.common.util.toCachedNewsResponse
import com.litmus7.news.database.NewsDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class NewsDbDataSource @Inject constructor(private val dao: NewsDao) : NewsDataSource {
    private val tag = NewsDbDataSource::class.simpleName

    override fun getNews(country: String): Flow<Result<NewsResponse>> {
        return dao.getAllArticles().map { articles ->
            Log.d(tag, "Db Data Loaded")
            if (articles.isNotEmpty()) {
                Result.Success(articles.toCachedNewsResponse())
            } else {
                Result.Error(NewsFetchException("Nothing Here!\nPlease connect to internet to fetch the latest news"))
            }
        }
    }

    override suspend fun putNews(article: Article) {
        dao.insertArticle(article)
    }

    override suspend fun deleteAllNews() {
        dao.deleteAllArticles()
    }
}