package com.example.news.modules

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.WebViewClient
import androidx.databinding.DataBindingUtil
import com.example.news.R
import com.example.news.databinding.ActivityNewsListBinding
import com.example.news.network.ConnectivityLiveData
import com.example.news.retrofit.ApiCallFactory
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class DetailNewsActivity : AppCompatActivity() {
    lateinit var connectivityLiveData: ConnectivityLiveData
    lateinit var dialog:Dialog
    var mComositeDisposable: CompositeDisposable?=null
    lateinit var newsListBinding: ActivityNewsListBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        newsListBinding = DataBindingUtil.setContentView(this, R.layout.activity_news_list)

        //creating dialog for no internet connection
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

        newsListBinding.webView.webViewClient= WebViewClient()
        mComositeDisposable=CompositeDisposable()
        var url: String =intent.getStringExtra("url")!!
        var intent=intent.getIntExtra("poistion",0)

        mComositeDisposable!!.add(
                ApiCallFactory.create().getNews(url)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({response->

                            Log.e("PRINT",response.totalResults.toString())
                            newsListBinding.article=response.articles[intent]
                            newsListBinding.webView.loadUrl(response.articles[intent].url)

                            val webSettings=newsListBinding.webView.settings
                            webSettings.javaScriptEnabled=true
                            newsListBinding.progressBar.visibility=View.GONE

                        },{error->
                            Log.e("ERROR",error.localizedMessage)
                        }
                        ))

    }


    override fun onBackPressed() {
        if(newsListBinding.webView.canGoBack())
        {
            newsListBinding.webView.goBack()
        }
        else{
            super.onBackPressed()
        }
    }
}