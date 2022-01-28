package com.litmus7.news.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.litmus7.news.databinding.FragmentNewsByTopicBinding
import com.litmus7.news.domain.Article
import com.litmus7.news.ui.activity.MainActivity
import com.litmus7.news.ui.activity.NewsDetailsActivity
import com.litmus7.news.ui.adapter.NewsAdapter
import com.litmus7.news.util.*

class NewsByTopicFragment : Fragment() {
    private var _binding: FragmentNewsByTopicBinding? = null
    private val binding get() = _binding!!
    private val newsList: ArrayList<Article> = arrayListOf()
    private var adapter: NewsAdapter? = null

    companion object {
        const val FRAGMENT_TAG = "news_topic_fragment_tag"
        private val TAG: String = NewsByTopicFragment::class.java.simpleName
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        // Inflate the layout for this fragment
        _binding = FragmentNewsByTopicBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = NewsAdapter(newsList)
        binding.rvNewsByTopic.adapter = adapter
        binding.rvNewsByTopic.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.rvNewsByTopic.setHasFixedSize(true)

        adapter?.setOnItemClickListener { article, _ ->
            // Launch NewsDetailsActivity
            val newsDetailsActivityIntent = Intent(activity, NewsDetailsActivity::class.java).apply {
                val bundle = Bundle().apply {
                    putString(NEWS_AUTHOR_KEY, article.author)
                    putString(NEWS_CONTENT_KEY, article.content)
                    putString(NEWS_DESCRIPTION_KEY, article.description)
                    putString(NEWS_PUBLISHED_AT_KEY, article.publishedAt)
                    putString(NEWS_SOURCE_KEY, article.source.name)
                    putString(NEWS_TITLE_KEY, article.title)
                    putString(NEWS_URL_KEY, article.url)
                    putString(NEWS_IMAGE_URL_KEY, article.urlToImage)
                }
                putExtra(NEWS_BUNDLE_EXTRAS_KEY, bundle)
            }
            startActivity(newsDetailsActivityIntent)
        }
    }

    fun onDataLoaded(newsList: List<Article>) {
        // Initialize Recycler View
        Log.d(TAG, "onDataLoaded():: ${newsList.size}")
        val oldSize = this.newsList.size
        this.newsList.clear()
        adapter?.notifyItemRangeRemoved(0, oldSize)
        this.newsList.addAll(newsList)
        adapter?.notifyItemRangeInserted(0, newsList.size)
        (activity as MainActivity).hasData = newsList.isNotEmpty()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}