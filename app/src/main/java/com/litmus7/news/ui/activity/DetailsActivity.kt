package com.litmus7.news.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.core.net.toUri
import com.litmus7.common.domain.Article
import com.litmus7.common.domain.Source
import com.litmus7.common.util.*
import com.litmus7.news.databinding.ActivityDetailsBinding

class DetailsActivity : BaseActivity() {
    private lateinit var binding: ActivityDetailsBinding
    private val tag = DetailsActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val articleBundle = intent?.getBundleExtra(NEWS_BUNDLE_EXTRAS_KEY)
        articleBundle?.apply {
            val id = getInt(NEWS_AUTHOR_ID)
            val author = getString(NEWS_AUTHOR_KEY) ?: ""
            val content = getString(NEWS_CONTENT_KEY) ?: ""
            val description = getString(NEWS_DESCRIPTION_KEY) ?: ""
            val publishedAt = getString(NEWS_PUBLISHED_AT_KEY) ?: ""
            val sourceName = getString(NEWS_SOURCE_NAME_KEY) ?: ""
            val sourceId = getString(NEWS_SOURCE_ID_KEY) ?: ""
            val title = getString(NEWS_TITLE_KEY) ?: ""
            val url = getString(NEWS_URL_KEY) ?: ""
            val urlToImage = getString(NEWS_IMAGE_URL_KEY) ?: ""

            binding.article = Article(
                id = id,
                author = author,
                content = content,
                description = description,
                publishedAt = publishedAt,
                cleanPublishDate = publishedAt.toCleanDate(),
                source = Source(sourceId, sourceName),
                title = title,
                url = url,
                urlToImage = urlToImage
            )

            // Apply an Underline on "Source"
            binding.tvReadMore.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW).apply {
                    data = url.toUri()
                }
                startActivity(intent)
            }
        }
    }
}