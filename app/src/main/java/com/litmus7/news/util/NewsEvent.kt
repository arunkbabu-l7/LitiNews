package com.litmus7.news.util

import com.litmus7.news.domain.Article


sealed class NewsEvent {
    class Success(val articles: List<Article>): NewsEvent()
    class Failure(val errorText: String): NewsEvent()
    object Loading: NewsEvent()
    object Empty: NewsEvent()
}
