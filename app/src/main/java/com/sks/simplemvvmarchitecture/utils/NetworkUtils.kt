package com.sks.simplemvvmarchitecture.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

/**
 * @author  Sumit Singh on 8/11/2020.
 */
object NetworkUtils {

    private fun getConnectivityManager(context: Context?): ConnectivityManager {
        return context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    }

    fun isNetworkConnected(context: Context): Boolean {
        val connectivityManager = getConnectivityManager(context)
        val activeNetwork = connectivityManager.activeNetwork
        val networkCapabilities = connectivityManager.getNetworkCapabilities(activeNetwork)
        return networkCapabilities != null &&
                networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }

}