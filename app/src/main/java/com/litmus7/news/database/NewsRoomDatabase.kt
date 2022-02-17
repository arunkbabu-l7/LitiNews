package com.litmus7.news.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.litmus7.common.domain.Article
import com.litmus7.common.domain.Source

@Database(entities = [Article::class, Source::class], version = 1)
abstract class NewsRoomDatabase : RoomDatabase() {
    abstract fun newsDao(): NewsDao
}