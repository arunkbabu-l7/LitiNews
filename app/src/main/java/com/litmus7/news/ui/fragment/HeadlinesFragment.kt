package com.litmus7.news.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.litmus7.common.domain.NewsResponse
import com.litmus7.common.util.Constants
import com.litmus7.news.databinding.FragmentHeadlinesBinding
import com.litmus7.news.ui.activity.DetailsActivity
import com.litmus7.news.ui.activity.HeadlinesActivity
import com.litmus7.news.ui.adapter.NewsAdapter

class HeadlinesFragment : Fragment() {
    private var _binding: FragmentHeadlinesBinding? = null
    private val binding get() = _binding!!
    private var adapter: NewsAdapter? = null

    companion object {
        const val FRAGMENT_TAG = "news_topic_fragment_tag"
        private val TAG: String = HeadlinesFragment::class.java.simpleName
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        // Inflate the layout for this fragment
        _binding = FragmentHeadlinesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = NewsAdapter()
        with(binding) {
            rvNewsByTopic.adapter = adapter
            rvNewsByTopic.setHasFixedSize(true)
        }

        adapter?.setOnItemClickListener { article, _ ->
            // Launch DetailsActivity
            val newsDetailsActivityIntent = Intent(activity, DetailsActivity::class.java).apply {
                val bundle = Bundle().apply {
                    putInt(Constants.NEWS_AUTHOR_ID, article.id)
                    putString(Constants.NEWS_AUTHOR_KEY, article.author)
                    putString(Constants.NEWS_CONTENT_KEY, article.content)
                    putString(Constants.NEWS_DESCRIPTION_KEY, article.description)
                    putString(Constants.NEWS_PUBLISHED_AT_KEY, article.publishedAt)
                    putString(Constants.NEWS_SOURCE_NAME_KEY, article.source.name)
                    putString(Constants.NEWS_TITLE_KEY, article.title)
                    putString(Constants.NEWS_URL_KEY, article.url)
                    putString(Constants.NEWS_IMAGE_URL_KEY, article.urlToImage)
                }
                putExtra(Constants.NEWS_BUNDLE_EXTRAS_KEY, bundle)
            }
            startActivity(newsDetailsActivityIntent)
        }
    }

    fun onDataLoaded(newsResponse: NewsResponse) {
        // Initialize Recycler View
        val newsArticles = newsResponse.articles
        Log.d(TAG, "onDataLoaded():: ${newsArticles.size}")

        adapter?.submitList(newsArticles)
        if (newsResponse.status == NewsResponse.STATUS_OK) {
            // hasNewData = True, if and only if the data is fetched from network and is not empty; False otherwise
            (activity as HeadlinesActivity).hasNewData = newsArticles.isNotEmpty()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}