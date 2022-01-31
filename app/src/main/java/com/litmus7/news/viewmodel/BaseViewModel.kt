package com.litmus7.news.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.litmus7.news.util.NewsEvent
import kotlinx.coroutines.CoroutineExceptionHandler

abstract class BaseViewModel: ViewModel() {
    protected val _allNews = MutableLiveData<NewsEvent>(NewsEvent.Empty)

    private val tag = BaseViewModel::class.simpleName

    /**
     * A RESOURCE LOCK to prevent sending multiple requests at once
     */
    protected var LOCK = false

    protected val exceptionHandler: CoroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        _allNews.postValue(NewsEvent.Failure(throwable.message.toString()))
        LOCK = false
    }

    override fun onCleared() {
        _allNews.value = NewsEvent.Empty
        super.onCleared()
    }
}