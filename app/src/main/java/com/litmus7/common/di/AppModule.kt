package com.litmus7.common.di

import com.litmus7.news.datasource.HeadlinesDataSource
import com.litmus7.news.datasource.NewsDbDataSource
import com.litmus7.news.repository.HeadlinesRepository
import com.litmus7.news.repository.NewsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object AppModule {

    @ViewModelScoped
    @Provides
    fun provideHeadlinesRepository(
        dataSource: HeadlinesDataSource,
        dbDataSource: NewsDbDataSource
    ): NewsRepository = HeadlinesRepository(dataSource, dbDataSource)
}