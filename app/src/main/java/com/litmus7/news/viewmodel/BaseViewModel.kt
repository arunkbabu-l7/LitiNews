package com.litmus7.news.viewmodel

import androidx.lifecycle.ViewModel

abstract class BaseViewModel: ViewModel() {
    private val tag = BaseViewModel::class.simpleName
    /**
     * A RESOURCE LOCK to prevent sending multiple requests at once
     */
    protected var LOCK = false
}