package com.litmus7.news.database

import com.litmus7.news.domain.Article
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NewsDbDataSource @Inject constructor(private val dao: NewsDao) {
    val allArticles: Flow<List<Article>> = dao.getAllArticles()

    suspend fun insertArticle(article: Article) {
        dao.insertArticle(article)
    }

    suspend fun deleteArticle(article: Article) {
        dao.deleteArticle(article)
    }

    suspend fun deleteAllArticles() {
        dao.deleteAllArticles()
    }
}