package com.litmus7.news.viewmodel

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.litmus7.common.domain.NewsResponse
import com.litmus7.common.util.NewsEvent
import com.litmus7.common.util.Result
import com.litmus7.news.repository.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HeadlinesViewModel @Inject constructor(
    private val headlinesRepository: NewsRepository
) : BaseViewModel() {
    var allNews: StateFlow<NewsEvent> = MutableStateFlow(NewsEvent.Empty)
        private set
    private val tag = HeadlinesViewModel::class.simpleName

    fun fetchTopHeadlines(country: String = "in") {
        Log.d(tag, "fetchTopHeadlines()")
        Log.d(tag, "LOCK: $LOCK")
        if (LOCK)
            return

        LOCK = true

        viewModelScope.launch(Dispatchers.IO) {
            val newsFlow: Flow<Result<NewsResponse>> = headlinesRepository.fetchNews(country)
            allNews = newsFlow.map { newsResult ->
                when (newsResult) {
                    is Result.Success -> {
                        Log.d(tag, "fetchTopHeadlines::onSuccess()")
                        LOCK = false
                        NewsEvent.Success(newsResult.data)
                    }
                    is Result.Error -> {
                        Log.d(tag, "fetchTopHeadlines::onFailure()")
                        LOCK = false
                        NewsEvent.Failure(newsResult.exception.message.toString())
                    }
                }
            }.stateIn(
                initialValue = NewsEvent.Loading,
                scope = viewModelScope,
                started = SharingStarted.Lazily
            )
        }
    }
}