package com.litmus7.news.domain

import android.os.Parcelable
import com.litmus7.news.util.toCleanDate
import kotlinx.parcelize.Parcelize

@Parcelize
data class Article(
    val author: String,
    val content: String,
    val description: String,
    val publishedAt: String,
    val cleanPublishDate: String = publishedAt.toCleanDate(),
    val source: Source,
    val title: String,
    val url: String,
    val urlToImage: String
) : Parcelable