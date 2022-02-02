package com.litmus7.news.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.litmus7.news.databinding.FragmentHeadlinesBinding
import com.litmus7.news.domain.Article
import com.litmus7.news.ui.activity.DetailsActivity
import com.litmus7.news.ui.activity.HeadlinesActivity
import com.litmus7.news.ui.adapter.NewsAdapter
import com.litmus7.news.util.*

class HeadlinesFragment : Fragment() {
    private var _binding: FragmentHeadlinesBinding? = null
    private val binding get() = _binding!!
    private val newsSet: MutableSet<Article> = mutableSetOf()
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
        adapter = NewsAdapter(newsSet)
        binding.rvNewsByTopic.adapter = adapter
        binding.rvNewsByTopic.setHasFixedSize(true)

        adapter?.setOnItemClickListener { article, _ ->
            // Launch DetailsActivity
            val newsDetailsActivityIntent = Intent(activity, DetailsActivity::class.java).apply {
                val bundle = Bundle().apply {
                    putString(NEWS_AUTHOR_KEY, article.author)
                    putString(NEWS_CONTENT_KEY, article.content)
                    putString(NEWS_DESCRIPTION_KEY, article.description)
                    putString(NEWS_PUBLISHED_AT_KEY, article.publishedAt)
                    putString(NEWS_SOURCE_NAME_KEY, article.source.name)
                    putString(NEWS_TITLE_KEY, article.title)
                    putString(NEWS_URL_KEY, article.url)
                    putString(NEWS_IMAGE_URL_KEY, article.urlToImage)
                }
                putExtra(NEWS_BUNDLE_EXTRAS_KEY, bundle)
            }
            startActivity(newsDetailsActivityIntent)
        }
    }

    fun onDataLoaded(newsArticles: List<Article>) {
        // Initialize Recycler View
        Log.d(TAG, "onDataLoaded():: ${newsArticles.size}")
        newsSet.addAll(newsArticles)
        adapter?.notifyItemRangeChanged(0, newsSet.size)
        (activity as HeadlinesActivity).hasData = newsArticles.isNotEmpty()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}