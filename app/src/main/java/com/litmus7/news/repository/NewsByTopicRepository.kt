package com.litmus7.news.repository

import com.litmus7.news.domain.NewsResponse
import com.litmus7.news.exception.NewsFetchException
import com.litmus7.news.network.NewsApi
import com.litmus7.news.util.Result
import retrofit2.Response
import javax.inject.Inject

class NewsByTopicRepository @Inject constructor(
    private val newsApi: NewsApi
) {
    suspend fun getNewsByTopic(topic: String): Result<NewsResponse> {
        val response: Response<NewsResponse> = newsApi.getNewsByTopic(topic)
        val result = response.body()
        return if (response.isSuccessful && result != null) {
            Result.Success(result)
        } else {
            Result.Error(NewsFetchException("Unable to Fetch News! Please try again"))
        }
    }
}