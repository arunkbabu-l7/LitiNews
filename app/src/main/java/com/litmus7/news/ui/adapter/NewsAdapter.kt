package com.litmus7.news.ui.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.litmus7.news.databinding.ItemNewsBinding
import com.litmus7.news.domain.Article
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
        val article: Article = newsList[position]
        holder.binding.article = article
        holder.binding.executePendingBindings()
        holder.bind(article)
    }

    override fun getItemCount() = newsList.size

    inner class NewsViewHolder(val binding: ItemNewsBinding, private val context: Context) : RecyclerView.ViewHolder(binding.root) {
        private val tag = NewsViewHolder::class.java.simpleName

        fun bind(article: Article) {
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