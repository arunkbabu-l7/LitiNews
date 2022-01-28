package com.litmus7.news.ui.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.litmus7.news.databinding.ItemNewsBinding
import com.litmus7.news.domain.Article
import com.litmus7.news.util.toCleanDate
import javax.annotation.Nullable

class NewsAdapter(private val newsList: List<Article>) : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {
    private val tag = NewsAdapter::class.java.simpleName
    private var itemClickListener: OnItemClickListener? = null
    private var setOnItemClickListener: ((Article, Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        Log.d(tag, "onCreateViewHolder()")
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val binding = ItemNewsBinding.inflate(inflater, parent, false)
        return NewsViewHolder(binding, context)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.bind(newsList[position])
    }

    override fun getItemCount() = newsList.size

    inner class NewsViewHolder(private val binding: ItemNewsBinding, private val context: Context) : RecyclerView.ViewHolder(binding.root) {
        private val tag = NewsViewHolder::class.java.simpleName

        fun bind(article: Article) {
            // Load the news image
            Glide.with(context)
                .load(article.urlToImage)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(binding.ivThumbnail)

            binding.tvNewsTitle.text = article.title
            binding.tvDate.text = article.publishedAt.toCleanDate()
            binding.tvSource.text = article.source.name
            Log.d(tag, "bind():: ${article.title}")

            binding.root.setOnClickListener {
                itemClickListener?.onItemClick(article, adapterPosition)
                setOnItemClickListener?.invoke(article, adapterPosition)
            }
        }
    }

    fun setOnItemClickListener(@Nullable listener: OnItemClickListener) {
        itemClickListener = listener
    }

    fun setOnItemClickListener(@Nullable listener: ((Article, Int) -> Unit)?) {
        setOnItemClickListener = listener
    }

    interface OnItemClickListener {
        fun onItemClick(view: Article, position: Int)
    }
}