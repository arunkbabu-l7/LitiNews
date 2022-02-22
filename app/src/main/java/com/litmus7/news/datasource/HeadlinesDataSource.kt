package com.litmus7.news.datasource

import android.util.Log
import com.litmus7.common.domain.NewsResponse
import com.litmus7.common.exception.NewsFetchException
import com.litmus7.common.network.NewsApi
import com.litmus7.common.util.Constants
import com.litmus7.common.util.Result
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import java.net.UnknownHostException
import javax.inject.Inject

class HeadlinesDataSource @Inject constructor(private val newsApi: NewsApi) : NewsDataSource {
    private val tag = HeadlinesDataSource::class.simpleName

    override fun getNews(country: String) = flow {
        Log.d(tag, "HeadlinesDataSource#getNews()")
        // Producer Block
        try {
            val response: Response<NewsResponse> = newsApi.getTopHeadlines(country)
            val result = response.body()
            if (response.isSuccessful && result != null) {
                emit(Result.Success(result))
            } else {
                emit(Result.Error(NewsFetchException(Constants.DEFAULT_ERROR_MESSAGE)))
            }
        } catch (e: UnknownHostException) {
            emit(Result.Error(NewsFetchException("Unable to Connect to Server! Please check your internet connection & try again!")))
        }
    }
}