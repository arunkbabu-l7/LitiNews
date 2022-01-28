package com.litmus7.news.network

import com.litmus7.news.domain.NewsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {
    @GET("/v2/everything")
    suspend fun getNewsByTopic(@Query("q") query: String) : Response<NewsResponse>

    @GET("/v2/top-headlines")
    suspend fun getTopHeadlines(@Query("country") country: String) : Response<NewsResponse>
}