package com.litmus7.common.di

import android.content.Context
import androidx.room.Room
import com.litmus7.news.database.NewsDao
import com.litmus7.news.database.NewsRoomDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    fun provideNewsDao(db: NewsRoomDatabase): NewsDao = db.newsDao()

    @Singleton
    @Provides
    fun provideNewsDatabase(@ApplicationContext context: Context): NewsRoomDatabase =
        Room.databaseBuilder(context, NewsRoomDatabase::class.java, "news_db")
            .fallbackToDestructiveMigration()
            .build()
}