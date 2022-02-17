package com.litmus7.common.network

import android.util.Log
import com.litmus7.common.domain.NewsResponse
import com.litmus7.common.exception.NewsFetchException
import com.litmus7.common.util.DEFAULT_ERROR_MESSAGE
import com.litmus7.common.util.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import java.net.UnknownHostException
import javax.inject.Inject

class HeadlinesDataSource @Inject constructor(private val newsApi: NewsApi) {
    private val tag = HeadlinesDataSource::class.simpleName

    suspend fun getTopHeadlines(country: String): Flow<Result<NewsResponse>> = flow {
        Log.d(tag, "HeadlinesDataSource#getTopHeadlines()")
        // Producer Block
        try {
            val response: Response<NewsResponse> = newsApi.getTopHeadlines(country)
            val result = response.body()
            if (response.isSuccessful && result != null) {
                emit(Result.Success(result))
            } else {
                emit(Result.Error(NewsFetchException(DEFAULT_ERROR_MESSAGE)))
            }
        } catch (e: UnknownHostException) {
            emit(Result.Error(NewsFetchException("Unable to Connect to Server! Please check your internet connection & try again!")))
        }
    }
}