package com.litmus7.common.di

import com.litmus7.common.network.HeadlinesDataSource
import com.litmus7.common.network.NewsApi
import com.litmus7.news.database.NewsDao
import com.litmus7.news.database.NewsDbDataSource
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