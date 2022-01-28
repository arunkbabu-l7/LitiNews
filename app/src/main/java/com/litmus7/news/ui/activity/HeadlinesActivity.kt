package com.litmus7.news.ui.activity

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.fragment.app.add
import androidx.fragment.app.commit
import com.litmus7.news.R
import com.litmus7.news.databinding.ActivityHeadlinesBinding
import com.litmus7.news.ui.fragment.HeadlinesFragment
import com.litmus7.news.util.NewsEvent
import com.litmus7.news.viewmodel.HeadlinesViewModel

class HeadlinesActivity : BaseActivity() {
    private lateinit var binding: ActivityHeadlinesBinding
    private val tag = HeadlinesActivity::class.java.simpleName
    private val viewModel: HeadlinesViewModel by viewModels()
    var hasData = false

    companion object {
        private const val HAS_DATA_SAVE_INSTANCE_KEY = "has_data_save_instance_key"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHeadlinesBinding.inflate(layoutInflater)
        progressBar = binding.progressBar
        errorLayout = binding.errorLayout.root
        setContentView(binding.root)

        val fragManager = supportFragmentManager
        if (savedInstanceState == null) {
            fragManager.commit {
                setReorderingAllowed(true)
                add<HeadlinesFragment>(R.id.fragment_container_view, HeadlinesFragment.FRAGMENT_TAG)
            }
        } else {
            hasData = savedInstanceState.getBoolean(HAS_DATA_SAVE_INSTANCE_KEY)
        }

        if (!hasData)
            viewModel.fetchTopHeadlines()

        viewModel.allNews.observe(this) { newsEvent ->
            when(newsEvent) {
                is NewsEvent.Success -> {
                    Log.i(tag, "Success")
                    hideProgressCircle()
                    binding.fragmentContainerView.isVisible = true
                    val fragment = fragManager.findFragmentByTag(HeadlinesFragment.FRAGMENT_TAG) as HeadlinesFragment?
                    fragment?.onDataLoaded(newsEvent.articles)
                }
                is NewsEvent.Failure -> {
                    Log.i(tag, "Failure")
                    showError(newsEvent.errorText)
                }
                is NewsEvent.Empty -> {
                    Log.i(tag, "Empty")
                }
                is NewsEvent.Loading -> {
                    Log.i(tag, "Loading")
                    binding.fragmentContainerView.isVisible = false
                    showProgressCircle()
                }
            }
        }
    }

    private fun showError(msg: String) {
        if (!hasData) {
            val isVisible = true
            binding.errorLayout.root.isVisible = isVisible
            binding.progressBar.isVisible = !isVisible
            binding.errorLayout.tvErrorText.text = msg
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean(HAS_DATA_SAVE_INSTANCE_KEY, hasData)
    }
}