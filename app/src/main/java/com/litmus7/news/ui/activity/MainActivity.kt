package com.litmus7.news.ui.activity

import android.os.Bundle
import android.util.Log
import androidx.core.view.isVisible
import androidx.fragment.app.add
import androidx.fragment.app.commit
import com.litmus7.news.R
import com.litmus7.news.databinding.ActivityMainBinding
import com.litmus7.news.ui.fragment.NewsByTopicFragment
import com.litmus7.news.util.NewsEvent

class MainActivity : BaseActivity() {
    private lateinit var binding: ActivityMainBinding
    private val tag = MainActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        progressBar = binding.progressBar
        errorLayout = binding.errorLayout.root
        setContentView(binding.root)

        val fragManager = supportFragmentManager
        if (savedInstanceState == null) {
            fragManager.commit {
                setReorderingAllowed(true)
                add<NewsByTopicFragment>(R.id.fragment_container_view, NewsByTopicFragment.FRAGMENT_TAG)
            }
        }

        viewModel.fetchNewsTopic("bitcoin")
        viewModel.allNews.observe(this) { newsEvent ->
            when(newsEvent) {
                is NewsEvent.Success -> {
                    Log.i(tag, "Success")
                    hideProgressCircle()
                    binding.fragmentContainerView.isVisible = true
                    val fragment = fragManager.findFragmentByTag(NewsByTopicFragment.FRAGMENT_TAG) as NewsByTopicFragment?
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
        val isVisible = true
        binding.errorLayout.root.isVisible = isVisible
        binding.progressBar.isVisible = !isVisible
        binding.errorLayout.tvErrorText.text = msg
    }
}