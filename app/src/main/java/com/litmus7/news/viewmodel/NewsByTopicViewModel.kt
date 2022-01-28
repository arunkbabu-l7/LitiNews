package com.litmus7.news.viewmodel

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.litmus7.news.domain.NewsResponse
import com.litmus7.news.repository.NewsRepository
import com.litmus7.news.util.NewsEvent
import com.litmus7.news.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsByTopicViewModel @Inject constructor(
    private val repository: NewsRepository
) : BaseViewModel() {
    private val tag = TopHeadlinesViewModel::class.simpleName

    fun fetchNewsTopic(topic: String = "bitcoin") {
        Log.d(tag, "fetchNewsTopic()")

        allNewsMutable.postValue(NewsEvent.Loading)

        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            when (val newsResult: Result<NewsResponse> = repository.getNewsByTopic(topic)) {
                is Result.Success -> {
                    allNewsMutable.postValue(NewsEvent.Success(newsResult.data.articles))
                }
                is Result.Error -> {
                    allNewsMutable.postValue(NewsEvent.Failure(newsResult.exception.message.toString()))
                }
            }
        }
    }
}