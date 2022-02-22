package com.litmus7.news.repository

import com.litmus7.common.domain.NewsResponse
import com.litmus7.common.exception.NewsFetchException
import com.litmus7.common.util.Constants
import com.litmus7.common.util.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

interface NewsRepository {
    fun fetchNews(country: String): Flow<Result<NewsResponse>> = flow {
        Result.Error(NewsFetchException(Constants.ERROR_METHOD_NOT_IMPLEMENTED))
    }
}