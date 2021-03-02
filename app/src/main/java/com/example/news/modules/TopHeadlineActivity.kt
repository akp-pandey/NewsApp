package com.example.news.modules

import android.app.Dialog
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.news.R
import com.example.news.adapter.TitleAdapter
import com.example.news.network.ConnectivityLiveData
import com.example.news.response.Article
import com.example.news.retrofit.ApiCallFactory
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.*
import kotlin.collections.ArrayList

class TopHeadlineActivity : AppCompatActivity() {
    var mComositeDisposable: CompositeDisposable? = null
    lateinit var url: String
    lateinit var dialog: Dialog
    lateinit var connectivityLiveData: ConnectivityLiveData
    var titleArrayList = ArrayList<Article>()

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        dialog = Dialog(this)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.nointernet_connection)
        connectivityLiveData = ConnectivityLiveData(this)
        connectivityLiveData.observe(this, androidx.lifecycle.Observer { isAvailable ->
            if (isAvailable) {
                dialog.dismiss()
                setUp()
            } else {
                dialog.show()
            }
        })
    }

    private fun setUp() {
        val calendar=Calendar.getInstance()
        val date=calendar.get(Calendar.DAY_OF_MONTH).toString()+"-"+calendar.get(Calendar.MONTH)+"-"+calendar.get(Calendar.YEAR)
        var recyclerView:RecyclerView=findViewById(R.id.rvNewsTitle)
        var progressBar:ProgressBar=findViewById(R.id.progressBar)
        var dates:TextView=findViewById(R.id.date)
        var heading:TextView=findViewById(R.id.heading)
        var intent=intent.getIntExtra("value",0)
        if(intent.equals(1)) {
            url =
                    "http://newsapi.org/v2/top-headlines?country=in&category=technology&apiKey=cc5007ba7b6d40b4ae6ae09cd6acaba7"
            heading.setText("TECHNOLOGY NEWS")
        }
        else if (intent.equals(2)) {
            url="http://newsapi.org/v2/top-headlines?country=in&category=entertainment&apiKey=cc5007ba7b6d40b4ae6ae09cd6acaba7"
            heading.setText("ENTERTAINMENT NEWS")
        }
        else if (intent.equals(3)) {
            url="http://newsapi.org/v2/top-headlines?country=in&category=sports&apiKey=cc5007ba7b6d40b4ae6ae09cd6acaba7"
            heading.setText("SPORTS NEWS")
        }
        else if(intent.equals(4)) {
            url="http://newsapi.org/v2/top-headlines?country=in&category=business&apiKey=cc5007ba7b6d40b4ae6ae09cd6acaba7"
            heading.setText("BUSINESS NEWS")
        }
        else if (intent.equals(5)) {
            url="http://newsapi.org/v2/top-headlines?country=in&category=science&apiKey=cc5007ba7b6d40b4ae6ae09cd6acaba7"
            heading.setText("SCIENCE NEWS")
        }
        else if (intent.equals(6)) {
            url="http://newsapi.org/v2/top-headlines?country=in&category=health&apiKey=cc5007ba7b6d40b4ae6ae09cd6acaba7"
            heading.setText("HEALTH NEWS")
        }
        else{
            url="v2/top-headlines?country=in&apiKey=cc5007ba7b6d40b4ae6ae09cd6acaba7"
            heading.setText("TOP HEADLINES")
        }
        val category:LinearLayout=findViewById(R.id.layoutCat)
        category.setOnClickListener {
            val intent=Intent(this, CategoryActivity::class.java)
            startActivity(intent)

        }
        dates.setText(date)

        recyclerView.addItemDecoration(DividerItemDecoration(this,LinearLayoutManager.VERTICAL))
        val Adapter = TitleAdapter({ item ->
        }, this, url)

        mComositeDisposable = CompositeDisposable()
        mComositeDisposable!!.add(ApiCallFactory.create().getNews(url)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response ->
                    titleArrayList.clear()
                    Log.e("PRINT", response.totalResults.toString())
                    for (i in 0 until response.articles.size) {
                        titleArrayList.add(response.articles[i])
                    }
                    Log.e("PRINT", titleArrayList.toString())
                    Adapter.submitList(titleArrayList)
                    recyclerView.adapter = Adapter
                    progressBar.visibility = View.GONE
                }, { error ->
                    Log.e("ERROR", error.localizedMessage)
                }
                ))

    }


}