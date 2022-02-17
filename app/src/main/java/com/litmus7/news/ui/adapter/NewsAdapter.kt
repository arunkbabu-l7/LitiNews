package com.litmus7.news.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.litmus7.common.domain.Article
import com.litmus7.news.databinding.ItemNewsBinding
import javax.annotation.Nullable

class NewsAdapter : ListAdapter<Article, NewsAdapter.NewsViewHolder>(ArticleComparator()) {
    private val tag = NewsAdapter::class.java.simpleName
    private var setOnItemClickListener: ((Article, Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val binding = ItemNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NewsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val article: Article = getItem(position)
        holder.run {
            with(binding) {
                this.article = article
                executePendingBindings()
            }
            bind(article)
        }
    }

    inner class NewsViewHolder(val binding: ItemNewsBinding) : RecyclerView.ViewHolder(binding.root) {
        private val tag = NewsViewHolder::class.java.simpleName

        fun bind(article: Article) {
            Log.d(tag, "bind():: ${article.title}")
            binding.root.setOnClickListener {
                setOnItemClickListener?.invoke(article, adapterPosition)
            }
        }
    }

    class ArticleComparator : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.title == newItem.title
        }
    }

    fun setOnItemClickListener(@Nullable listener: ((Article, Int) -> Unit)?) {
        setOnItemClickListener = listener
    }
}