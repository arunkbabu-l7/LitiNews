package com.litmus7.news.util

import com.litmus7.news.domain.NewsResponse

sealed class NewsEvent {
    class Success(val newsResponse: NewsResponse): NewsEvent()
    class Failure(val errorText: String): NewsEvent()
    object Loading: NewsEvent()
    object Empty: NewsEvent()
}
