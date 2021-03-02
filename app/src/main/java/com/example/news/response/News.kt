package com.example.news.response

data class News<T>(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)