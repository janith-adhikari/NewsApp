package com.ewind.newsapptest.util.ext

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity

/**
 * Checks network connectivity
 *
 * context.isOnline{
 *      network request
 * }
 */
const val MSG_NO_INTERNET = "No Internet, Please connect to Wifi or mobile network"

fun Context.isOnline(funs: () -> Unit): Boolean {
    return if (isNetworkStatusAvailable()) {
        funs(); true
    } else {
        if (isNetworkStatusAvailableAPIM()) {
            funs(); true
        } else {
            false
        }
    }
}

@RequiresApi(Build.VERSION_CODES.M)
fun Context.isNetworkStatusAvailableAPIM(): Boolean {
    val connectivityManager =
        getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    if (connectivityManager != null) {
        val capabilities =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        if (capabilities != null) {
            if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                return true
            } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                return true
            } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                return true
            }
        }
    }
    MSG_NO_INTERNET.showToast(this)
    return false
}

/*@RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
fun Context.isNetworkStatusAvailable(): Boolean {
    val connectivityManager = this
        .getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager
    connectivityManager?.let {
        val netInfo = it.activeNetworkInfo
        netInfo?.let {
            if (netInfo.isConnected) return true
        }
    }
    this.showError(MSG_NO_INTERNET)
    return false
}*/

fun Context.isNetworkStatusAvailable(): Boolean {
    val cm = this.getSystemService(AppCompatActivity.CONNECTIVITY_SERVICE) as ConnectivityManager
    val networks: Array<Network> = cm.allNetworks
    var hasInternet = false
    if (networks.isNotEmpty()) {
        for (network in networks) {
            val nc = cm.getNetworkCapabilities(network)
            if (nc != null) {
                if (nc.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)) hasInternet =
                    true
            }
        }
    }
    return hasInternet
}
