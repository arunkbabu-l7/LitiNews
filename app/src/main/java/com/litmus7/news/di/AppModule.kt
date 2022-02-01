package com.litmus7.news.di

import com.litmus7.news.network.HeadlinesDataSource
import com.litmus7.news.network.NewsApi
import com.litmus7.news.repository.HeadlinesRepository
import com.litmus7.news.repository.NewsByTopicRepository
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
    fun provideHeadlinesDataSource(newsApi: NewsApi): HeadlinesDataSource = HeadlinesDataSource(newsApi)

    @ViewModelScoped
    @Provides
    fun provideHeadlinesRepository(dataSource: HeadlinesDataSource): HeadlinesRepository = HeadlinesRepository(dataSource)

    @ViewModelScoped
    @Provides
    fun provideNewsByTopicRepository(newsApi: NewsApi): NewsByTopicRepository = NewsByTopicRepository(newsApi)
}