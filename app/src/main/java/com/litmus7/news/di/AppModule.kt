package com.litmus7.news.di

import com.litmus7.news.BuildConfig
import com.litmus7.news.network.NewsApi
import com.litmus7.news.repository.NewsRepository
import com.litmus7.news.repository.NewsRepositoryImpl
import com.litmus7.news.util.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        val client = OkHttpClient.Builder()
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        client.addInterceptor(loggingInterceptor)
        client.addNetworkInterceptor { chain ->
            val originalRequest: Request = chain.request()
            val newRequest: Request = originalRequest.newBuilder()
                .header("X-Api-Key", BuildConfig.API_KEY)
                .build()

            chain.proceed(newRequest)
        }
        return client.build()
    }

    @Singleton
    @Provides
    fun provideNewsApi(client: OkHttpClient): NewsApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NewsApi::class.java)
    }

    @Provides
    fun provideNewsRepository(newsApi: NewsApi): NewsRepository = NewsRepositoryImpl(newsApi)
}