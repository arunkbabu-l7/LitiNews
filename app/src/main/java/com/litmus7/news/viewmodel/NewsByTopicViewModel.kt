package com.litmus7.news.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.litmus7.news.domain.NewsResponse
import com.litmus7.news.repository.NewsByTopicRepository
import com.litmus7.news.util.NewsEvent
import com.litmus7.news.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsByTopicViewModel @Inject constructor(
    private val repository: NewsByTopicRepository
) : BaseViewModel() {
    val allNews: LiveData<NewsEvent> = _allNews
    private val tag = HeadlinesViewModel::class.simpleName

    fun fetchNewsTopic(topic: String = "bitcoin") {
        Log.d(tag, "fetchNewsTopic()")

        _allNews.postValue(NewsEvent.Loading)

        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            when (val newsResult: Result<NewsResponse> = repository.getNewsByTopic(topic)) {
                is Result.Success -> {
                    _allNews.postValue(NewsEvent.Success(newsResult.data.articles))
                }
                is Result.Error -> {
                    _allNews.postValue(NewsEvent.Failure(newsResult.exception.message.toString()))
                }
            }
        }
    }
}