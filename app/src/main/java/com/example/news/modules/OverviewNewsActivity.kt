package com.example.news.modules

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.example.news.R
import com.example.news.adapter.MainPageAdapter
import com.example.news.network.ConnectivityLiveData
import com.example.news.response.Article
import com.example.news.retrofit.ApiCallFactory
import com.google.android.material.snackbar.Snackbar
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers


class OverviewNewsActivity : AppCompatActivity() {

    //Variable declared
    lateinit var connectivityLiveData: ConnectivityLiveData
    lateinit var compositeDisposable: CompositeDisposable
    var arryList=ArrayList<Article>()
    private lateinit var dialog:Dialog

    @SuppressLint("ClickableViewAccessibility", "UseSwitchCompatOrMaterialCode")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_flipper_screen)

        val switch:Switch=findViewById(R.id.mode)

        //For night and Day Mode
        switch.setOnCheckedChangeListener { buttonView, isChecked ->
            if (switch.isChecked)
            {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                switch.text="LIGHT MODE"
            }else
            {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                switch.text="DARK MODE"
            }
        }

        //Creating dialog for no internet connection
        dialog= Dialog(this)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.nointernet_connection)
        connectivityLiveData= ConnectivityLiveData(this)

        connectivityLiveData.observe(this, Observer { isAvailable->
            if (isAvailable) {
                dialog.dismiss()
                setUp()
            }
            else {
                dialog.show()
            }
        })


    }
    private fun setUp() {

        val imageDetail:ImageView=findViewById(R.id.detailNews)
        imageDetail.setOnClickListener {
            val intent=Intent(this, TopHeadlineActivity::class.java)
            startActivity(intent)
        }
        var loadingBar:ProgressBar=findViewById(R.id.loadingBar)
        var recyclerView:RecyclerView=findViewById(R.id.mainPage)

        val adapter= MainPageAdapter { mainpage ->

        }

        //ApiCall
        compositeDisposable= CompositeDisposable()
        compositeDisposable.add(ApiCallFactory.create().getNews("v2/top-headlines?country=in&apiKey=cc5007ba7b6d40b4ae6ae09cd6acaba7")
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({response->
                            arryList.clear()
                            loadingBar.visibility= View.VISIBLE
                            arryList.addAll(response.articles)
                            adapter.submitList(arryList)
                            recyclerView.adapter=adapter
                            loadingBar.visibility= View.INVISIBLE

                        },{error->

                        }))
    }



}


