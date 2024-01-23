package com.example.todoapp.core.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

object CommonUtil {

    fun hasInternetConnection(context: Context): Boolean {

        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetwork

        return if (activeNetwork != null) {

            val networkCapabilities = connectivityManager.getNetworkCapabilities(activeNetwork)

            networkCapabilities!!.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                    networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)

        }else{
            false

        }


    }
}