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
) : Parcelable {
    override fun hashCode(): Int {
        return title.hashCode()
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Article

        if (author != other.author) return false
        if (content != other.content) return false
        if (description != other.description) return false
        if (publishedAt != other.publishedAt) return false
        if (cleanPublishDate != other.cleanPublishDate) return false
        if (source != other.source) return false
        if (title != other.title) return false
        if (url != other.url) return false
        if (urlToImage != other.urlToImage) return false

        return true
    }
}