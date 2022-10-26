package com.application.portdex.Uitls

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import android.widget.Toast
import java.util.*

object GeneralFunctions {

    fun getUUID(context: Context) : String{
       return UUID.randomUUID().toString();
    }

    fun isNetworkAvailable(context:Context): Boolean
    {
        if (context == null) return false
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (connectivityManager != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
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
            } else {
                try {
                    val activeNetworkInfo = connectivityManager.activeNetworkInfo
                    if (activeNetworkInfo != null && activeNetworkInfo.isConnected) {
                        Log.i("update_status", "Network is available")
                        return true
                    }
                } catch (e: java.lang.Exception) {
                    Log.i("update_status", "" + e.message)
                }
            }
        }
        Log.i("update_status", "Network is not available")
        return false
    }


    fun showInternetToast(context: Context?) {
        Toast.makeText(context, "Check your internet connection", Toast.LENGTH_SHORT).show()
    }


}