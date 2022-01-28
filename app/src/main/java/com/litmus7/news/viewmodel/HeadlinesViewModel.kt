package com.litmus7.news.viewmodel

import android.util.Log
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
    private val tag = HeadlinesViewModel::class.simpleName

    fun fetchTopHeadlines(country: String = "in") {
        Log.d(tag, "fetchTopHeadlines()")
        allNewsMutable.postValue(NewsEvent.Loading)

        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            when(val newsResult: Result<NewsResponse> = repository.getTopHeadlines(country)) {
                is Result.Success -> {
                    allNewsMutable.postValue(NewsEvent.Success(newsResult.data.articles))
                }
                is Result.Error -> {
                    allNewsMutable.postValue((NewsEvent.Failure(newsResult.exception.message.toString())))
                }
            }
        }
    }
}