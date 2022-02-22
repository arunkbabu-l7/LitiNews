package com.litmus7.common.util

import com.litmus7.common.domain.NewsResponse

sealed class NewsEvent {
    class Success(val newsResponse: NewsResponse): NewsEvent()
    class Failure(val errorText: String): NewsEvent()
    object Loading : NewsEvent()
    object Empty: NewsEvent()
}
