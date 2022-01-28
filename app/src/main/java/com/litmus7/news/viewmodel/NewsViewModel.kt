package com.litmus7.news.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.litmus7.news.domain.NewsResponse
import com.litmus7.news.repository.NewsRepository
import com.litmus7.news.util.NewsEvent
import com.litmus7.news.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val repository: NewsRepository
) : BaseViewModel() {
    private val _allNews: MutableLiveData<NewsEvent> = MutableLiveData(NewsEvent.Empty)
    val allNews: LiveData<NewsEvent> = _allNews

    private val exceptionHandler: CoroutineExceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        _allNews.postValue(NewsEvent.Failure(throwable.message.toString()))
    }

    fun fetchNewsTopic(topic: String) {
        _allNews.postValue(NewsEvent.Loading)

        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            when (val newsResult: Result<NewsResponse> = repository.getAllNews(topic)) {
                is Result.Success -> {
                    _allNews.postValue(NewsEvent.Success(newsResult.data.articles))
                }
                is Result.Error -> {
                    _allNews.postValue(NewsEvent.Failure("Failure to Load News"))
                }
            }
        }
    }
}