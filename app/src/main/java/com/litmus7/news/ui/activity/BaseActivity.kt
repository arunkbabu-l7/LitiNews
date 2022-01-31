package com.litmus7.news.ui.activity

import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
abstract class BaseActivity : AppCompatActivity() {
    var progressBar: ProgressBar? = null
    var errorLayout: View? = null
    var toast: Toast? = null

    fun showToast(msg: String) {
        toast?.cancel()
        toast = Toast.makeText(this, msg, Toast.LENGTH_SHORT)
        toast?.show()
    }

    fun showLongToast(msg: String) {
        toast?.cancel()
        toast = Toast.makeText(this, msg, Toast.LENGTH_LONG)
        toast?.show()
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