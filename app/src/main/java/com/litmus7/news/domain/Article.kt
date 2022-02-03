package com.litmus7.news.domain

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.litmus7.news.util.toCleanDate
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "article")
data class Article(
    @PrimaryKey(autoGenerate = true) val articleId: Int,
    val author: String,
    val content: String,
    val description: String,
    val publishedAt: String,
    @Ignore val cleanPublishDate: String = publishedAt.toCleanDate(),
    @Embedded val source: Source,
    val title: String,
    val url: String,
    val urlToImage: String
) : Parcelable