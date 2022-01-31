package com.litmus7.news.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.litmus7.news.domain.NewsResponse
import com.litmus7.news.repository.HeadlinesRepository
import com.litmus7.news.util.NewsEvent
import com.litmus7.news.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HeadlinesViewModel @Inject constructor(
    private val repository: HeadlinesRepository
) : BaseViewModel() {
    val allNews: LiveData<NewsEvent> = _allNews
    private val tag = HeadlinesViewModel::class.simpleName

    fun fetchTopHeadlines(country: String = "in") {
        Log.d(tag, "fetchTopHeadlines()")
        Log.d(tag, "LOCK: $LOCK")
        if (LOCK) return

        _allNews.postValue(NewsEvent.Loading)
        LOCK = true

        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            when(val newsResult: Result<NewsResponse> = repository.getTopHeadlines(country)) {
                is Result.Success -> {
                    Log.d(tag, "fetchTopHeadlines::onSuccess()")
                    LOCK = false
                    _allNews.postValue(NewsEvent.Success(newsResult.data.articles))
                }
                is Result.Error -> {
                    Log.d(tag, "fetchTopHeadlines::onFailure()")
                    LOCK = false
                    _allNews.postValue((NewsEvent.Failure(newsResult.exception.message.toString())))
                }
            }
        }
    }
}