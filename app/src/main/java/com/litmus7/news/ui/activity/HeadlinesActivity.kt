package com.litmus7.news.ui.activity

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.fragment.app.add
import androidx.fragment.app.commit
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.litmus7.news.R
import com.litmus7.news.databinding.ActivityHeadlinesBinding
import com.litmus7.news.ui.fragment.HeadlinesFragment
import com.litmus7.news.util.NetworkUtils
import com.litmus7.news.util.NewsEvent
import com.litmus7.news.viewmodel.HeadlinesViewModel
import kotlinx.coroutines.launch
import java.util.*
import kotlin.concurrent.timerTask

class HeadlinesActivity : BaseActivity() {
    private lateinit var binding: ActivityHeadlinesBinding
    private val tag = HeadlinesActivity::class.java.simpleName
    private val viewModel: HeadlinesViewModel by viewModels()
    var hasNewData = false

    companion object {
        private const val HAS_DATA_SAVE_INSTANCE_KEY = "key_has_data_save_instance"
    }

    // Network Callback
    private val networkCallback = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            Log.d(tag, "onAvailable()")
            NetworkUtils.isInternetConnected = true

            Timer().schedule(timerTask {
                runOnUiThread {
                    if (!hasNewData) {
                        viewModel.fetchTopHeadlines()
                        collectNews()
                    }
                }
            }, 1500)
        }

        override fun onLost(network: Network) {
            Log.d(tag, "onLost()")
            NetworkUtils.isInternetConnected = false

            runOnUiThread {
                if (!hasNewData) {
                    showError(getString(R.string.err_no_internet))
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHeadlinesBinding.inflate(layoutInflater)
        progressBar = binding.progressBar
        errorLayout = binding.errorLayout.root
        setContentView(binding.root)

        // Register Network Callback
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            cm.registerDefaultNetworkCallback(networkCallback)
        }

        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                add<HeadlinesFragment>(R.id.fragment_container_view, HeadlinesFragment.FRAGMENT_TAG)
            }
        } else {
            hasNewData = savedInstanceState.getBoolean(HAS_DATA_SAVE_INSTANCE_KEY)
        }

        if (!hasNewData) {
            viewModel.fetchTopHeadlines()
        }

        collectNews()
    }

    private fun collectNews() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.allNews.collect { newsEvent ->
                    when (newsEvent) {
                        is NewsEvent.Success -> {
                            Log.i(tag, "Success")
                            hideProgressCircle()
                            binding.fragmentContainerView.isVisible = true
                            val fragment = supportFragmentManager.findFragmentByTag(HeadlinesFragment.FRAGMENT_TAG) as HeadlinesFragment?
                            fragment?.onDataLoaded(newsEvent.newsResponse)
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
        }
    }

    private fun showError(msg: String) {
        if (!hasNewData) {
            val isVisible = true
            binding.errorLayout.root.isVisible = isVisible
            binding.progressBar.isVisible = !isVisible
            binding.fragmentContainerView.isVisible = !isVisible
            binding.errorLayout.tvErrorText.text = msg
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean(HAS_DATA_SAVE_INSTANCE_KEY, hasNewData)
    }
}