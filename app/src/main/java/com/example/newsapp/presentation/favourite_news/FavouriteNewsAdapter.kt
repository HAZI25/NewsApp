package com.example.newsapp.presentation.favourite_news

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.newsapp.R
import com.example.newsapp.databinding.ItemArticleWithImageBinding
import com.example.newsapp.databinding.ItemArticleWithoutImageBinding
import com.example.newsapp.presentation.model.Article

class FavouriteNewsAdapter(private val listener: FavouriteNewsAdapter.OnItemClickListener) :
    RecyclerView.Adapter<FavouriteNewsAdapter.ArticleViewHolder>() {

    val differ = AsyncListDiffer(this, NEWS_COMPARATOR)

    inner class ArticleViewHolder(private val binding: ViewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val item = differ.currentList[position]
                    if (item != null) {
                        listener.onItemClick(item)
                    }
                }
            }
        }

        fun bind(article: Article) {
            when (binding) {
                is ItemArticleWithoutImageBinding -> {
                    binding.tvSource.text = article.source.name
                    binding.tvTitle.text = article.title
                    binding.tvPublishedAt.text = article.publishedAt
                    if (article.description == null) {
                        binding.tvDescription.visibility = View.GONE
                    } else {
                        binding.tvDescription.visibility = View.VISIBLE
                        binding.tvDescription.text = article.description
                    }
                }
                is ItemArticleWithImageBinding -> {
                    Glide.with(binding.root)
                        .load(article.urlToImage)
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .error(R.drawable.ic_error)
                        .into(binding.ivArticleImage)
                    binding.tvSource.text = article.source.name
                    binding.tvTitle.text = article.title
                    binding.tvDescription.text = article.description
                    binding.tvPublishedAt.text = article.publishedAt
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val binding = when (viewType) {
            VIEW_TYPE_WITH_IMAGE -> {
                ItemArticleWithImageBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            }
            else -> {
                ItemArticleWithoutImageBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            }

        }
        return ArticleViewHolder(binding)
    }

    override fun getItemViewType(position: Int): Int {
        val item = differ.currentList[position]
        return if (item?.urlToImage == null) {
            VIEW_TYPE_WITHOUT_IMAGE
        } else VIEW_TYPE_WITH_IMAGE
    }

    interface OnItemClickListener {
        fun onItemClick(article: Article)
    }

    companion object {
        private const val VIEW_TYPE_WITH_IMAGE = 0
        private const val VIEW_TYPE_WITHOUT_IMAGE = 1

        val NEWS_COMPARATOR = object : DiffUtil.ItemCallback<Article>() {
            override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
                return oldItem.url == newItem.url
            }

            override fun areContentsTheSame(
                oldItem: Article,
                newItem: Article,
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val currentArticle = differ.currentList[position]

        if (currentArticle != null) {
            holder.bind(currentArticle)
        }
    }
}