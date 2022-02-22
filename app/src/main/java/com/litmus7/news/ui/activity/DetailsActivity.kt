package com.litmus7.news.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.core.net.toUri
import com.litmus7.common.domain.Article
import com.litmus7.common.domain.Source
import com.litmus7.common.util.Constants
import com.litmus7.common.util.toCleanDate
import com.litmus7.news.databinding.ActivityDetailsBinding

class DetailsActivity : BaseActivity() {
    private lateinit var binding: ActivityDetailsBinding
    private val tag = DetailsActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val articleBundle = intent?.getBundleExtra(Constants.NEWS_BUNDLE_EXTRAS_KEY)
        articleBundle?.apply {
            val id = getInt(Constants.NEWS_AUTHOR_ID)
            val author = getString(Constants.NEWS_AUTHOR_KEY) ?: ""
            val content = getString(Constants.NEWS_CONTENT_KEY) ?: ""
            val description = getString(Constants.NEWS_DESCRIPTION_KEY) ?: ""
            val publishedAt = getString(Constants.NEWS_PUBLISHED_AT_KEY) ?: ""
            val sourceName = getString(Constants.NEWS_SOURCE_NAME_KEY) ?: ""
            val sourceId = getString(Constants.NEWS_SOURCE_ID_KEY) ?: ""
            val title = getString(Constants.NEWS_TITLE_KEY) ?: ""
            val url = getString(Constants.NEWS_URL_KEY) ?: ""
            val urlToImage = getString(Constants.NEWS_IMAGE_URL_KEY) ?: ""

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

            binding.tvReadMore.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW).apply {
                    data = url.toUri()
                }
                startActivity(intent)
            }
        }
    }
}