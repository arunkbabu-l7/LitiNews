package com.litmus7.news.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.litmus7.news.util.NewsEvent
import kotlinx.coroutines.CoroutineExceptionHandler

abstract class BaseViewModel: ViewModel() {
    protected val allNewsMutable = MutableLiveData<NewsEvent>(NewsEvent.Empty)
    val allNews: LiveData<NewsEvent> get() = allNewsMutable

    private val tag = BaseViewModel::class.simpleName

    /**
     * A RESOURCE LOCK to prevent sending multiple requests at once
     */
    protected var LOCK = false

    protected val exceptionHandler: CoroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        allNewsMutable.postValue(NewsEvent.Failure(throwable.message.toString()))
        LOCK = false
    }

    override fun onCleared() {
        allNewsMutable.value = NewsEvent.Empty
        super.onCleared()
    }
}