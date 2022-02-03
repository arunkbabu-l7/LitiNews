package com.litmus7.news.domain

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "source")
data class Source(
    @PrimaryKey val id: String,
    val name: String
) : Parcelable