package com.example.news.retrofit

import com.example.news.response.Article
import com.example.news.response.News
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Url
import java.util.*

interface ApiHelper {
    @GET
    fun getNews(@Url url: String):Observable<News<Article>>


}