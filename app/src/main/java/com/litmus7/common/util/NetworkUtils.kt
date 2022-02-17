package com.litmus7.common.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build

class NetworkUtils {
    companion object {
        var isInternetConnected = false
    }

    /**
     * Checks whether Internet Connection is available
     * @param context The application Context
     * @return True if the device has active internet connection; False otherwise
     */
    fun isInternetAvailable(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        var isConnected = false
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val network = cm.getNetworkCapabilities(cm.activeNetwork)
            if (network != null) {
                isConnected = network.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            }
        } else {
            isConnected = cm.activeNetworkInfo?.isConnected ?: false
        }
        isInternetConnected = isConnected
        return isConnected
    }
}