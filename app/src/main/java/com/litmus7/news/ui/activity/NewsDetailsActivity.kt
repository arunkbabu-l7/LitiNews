package com.litmus7.news.ui.activity

import android.content.Intent
import android.os.Bundle
import android.text.SpannableString
import android.text.style.UnderlineSpan
import android.util.Log
import androidx.core.net.toUri
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.litmus7.news.databinding.ActivityNewsDetailsBinding
import com.litmus7.news.util.*

class NewsDetailsActivity : BaseActivity() {
    private lateinit var binding: ActivityNewsDetailsBinding
    private val tag = NewsDetailsActivity::class.java.simpleName

    private var author = ""
    private var content = ""
    private var description = ""
    private var publishedAt = ""
    private var source = ""
    private var title = ""
    private var url = ""
    private var urlToImage = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewsDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Log.d(tag, "onCreate()")

        val articleBundle = intent?.getBundleExtra(NEWS_BUNDLE_EXTRAS_KEY)

        articleBundle?.let {
            author = articleBundle.getString(NEWS_AUTHOR_KEY) ?: ""
            content = articleBundle.getString(NEWS_CONTENT_KEY) ?: ""
            description = articleBundle.getString(NEWS_DESCRIPTION_KEY) ?: ""
            publishedAt = articleBundle.getString(NEWS_PUBLISHED_AT_KEY) ?: ""
            source = articleBundle.getString(NEWS_SOURCE_KEY) ?: ""
            title = articleBundle.getString(NEWS_TITLE_KEY) ?: ""
            url = articleBundle.getString(NEWS_URL_KEY) ?: ""
            urlToImage = articleBundle.getString(NEWS_IMAGE_URL_KEY) ?: ""
        }

        // Load Image
        Glide.with(this)
            .load(urlToImage)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(binding.ivNewsImage)

        // Apply an Underline on "Source"
        val underlineString = SpannableString(source)
        underlineString.setSpan(UnderlineSpan(), 0, source.length, 0)
        binding.tvSource.text = underlineString
        binding.tvSource.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW).apply {
                data = url.toUri()
            }
            startActivity(intent)
        }

        binding.tvTitle.text = title
        binding.tvAuthor.text = author
        binding.tvDate.text = publishedAt.toCleanDate()
        binding.tvDescription.text = content
    }
}