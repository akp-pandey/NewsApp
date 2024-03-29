package com.example.news.network

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkInfo
import android.net.NetworkRequest
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData

class ConnectivityLiveData(val context: Context):LiveData<Boolean>() {
    private var connectivityManager:ConnectivityManager=context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onActive() {
        super.onActive()

        //val builder=NetworkRequest.Builder()
       // connectivityManager.registerNetworkCallback(builder.build(),connectivityCallBack)
        context.registerReceiver(networkReciever, IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"))
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onInactive() {
        super.onInactive()
        context.unregisterReceiver(networkReciever)
    }
    private val networkReciever=object: BroadcastReceiver() {
        @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
        override fun onReceive(context: Context, intent: Intent) {
            updateConnection()
        }

    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun updateConnection() {
    val activeNetwork:NetworkInfo?=connectivityManager.activeNetworkInfo
        postValue(activeNetwork?.isConnected==true)
    }
}