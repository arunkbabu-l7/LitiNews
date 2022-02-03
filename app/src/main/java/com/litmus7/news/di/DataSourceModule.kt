package com.litmus7.news.di

import com.litmus7.news.database.NewsDao
import com.litmus7.news.database.NewsDbDataSource
import com.litmus7.news.network.HeadlinesDataSource
import com.litmus7.news.network.NewsApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {
    @Provides
    fun provideHeadlinesDataSource(newsApi: NewsApi) = HeadlinesDataSource(newsApi)

    @Provides
    fun provideNewsDbDataSource(dao: NewsDao) = NewsDbDataSource(dao)
}