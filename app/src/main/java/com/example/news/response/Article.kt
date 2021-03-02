package com.example.news.response

import androidx.recyclerview.widget.DiffUtil

data class Article(
    val author: Any,
    val content: String,
    val description: String,
    val publishedAt: String,
    var source: Source,
    val title: String,
    val url: String,
    val urlToImage: String
)
object DiffArticel:DiffUtil.ItemCallback<Article>() {
    override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean{
        return oldItem==newItem
    }
    override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
       return oldItem.equals(newItem)
    }
}