package com.litmus7.news.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.litmus7.news.R

@BindingAdapter("app:loadImage")
fun loadImage(imageView: ImageView, url: String?) {
    Glide.with(imageView)
        .load(url)
        .error(R.drawable.news_err_drawable)
        .transition(DrawableTransitionOptions.withCrossFade())
        .into(imageView)
}