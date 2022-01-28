package com.litmus7.news.ui.activity

import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.litmus7.news.viewmodel.NewsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
abstract class BaseActivity : AppCompatActivity() {
    var progressBar: ProgressBar? = null
    var errorLayout: View? = null
    val viewModel: NewsViewModel by viewModels()

    fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    fun showLongToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
    }

    fun showProgressCircle() {
        val isVisible = true
        progressBar?.isVisible = isVisible
        errorLayout?.isVisible = !isVisible
    }

    fun hideProgressCircle() {
        progressBar?.isVisible = false
    }
}