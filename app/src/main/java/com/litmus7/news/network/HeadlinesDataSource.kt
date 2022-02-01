package com.litmus7.news.network

import android.util.Log
import com.litmus7.news.domain.NewsResponse
import com.litmus7.news.exception.NewsFetchException
import com.litmus7.news.util.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import javax.inject.Inject

var hasNewData = false

class HeadlinesDataSource @Inject constructor(private val newsApi: NewsApi) {
    private val tag = HeadlinesDataSource::class.simpleName

    suspend fun getTopHeadlines(country: String): Flow<Result<NewsResponse>> = flow {
        Log.d(tag, "HeadlinesDataSource#getTopHeadlines()")
        hasNewData = true
        // Producer Block
        val response: Response<NewsResponse> = newsApi.getTopHeadlines(country)
        val result = response.body()
        if (response.isSuccessful && result != null) {
            emit(Result.Success(result))
        } else {
            emit(Result.Error(NewsFetchException("Unable to Fetch News! Please Try again")))
        }
    }
}