package com.ephotos.common

import android.app.Activity
import android.content.Context
import android.content.res.Resources
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import kotlin.math.roundToInt

internal val Int.dp: Int
    get() = (this / Resources.getSystem().displayMetrics.density).roundToInt()
internal val Int.sp: Int
    get() = (this / Resources.getSystem().displayMetrics.scaledDensity).roundToInt()
internal val Int.dpPx: Int
    get() = (this * Resources.getSystem().displayMetrics.density).roundToInt()
internal val Int.spPx: Int
    get() = (this * Resources.getSystem().displayMetrics.scaledDensity).roundToInt()

fun Activity.loge(message: String) {
    Log.e(this::class.java.simpleName, message)
}

fun Activity.shortToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Activity.longtoast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}

fun noCrash(enableLog: Boolean = true, func: () -> Unit): String? {
    return try {
        func()
        null
    } catch (e: Exception) {
        if (enableLog)
            e.printStackTrace()
        e.message
    }
}


fun View.show() {
    this.visibility = View.VISIBLE
}

fun View.hide() {
    this.visibility = View.GONE
}

fun View.invisible() {
    this.visibility = View.INVISIBLE
}

val Fragment.isNetworkConnected: Boolean
    get() {
        val connectivityManager =
            requireActivity().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.M -> {
                val nw = connectivityManager.activeNetwork ?: return false
                val actNw = connectivityManager.getNetworkCapabilities(nw) ?: return false
                when {
                    actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                    actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                    else -> false
                }
            }
            else -> {
                // Use depreciated methods only on older devices
                val nwInfo = connectivityManager.activeNetworkInfo ?: return false
                nwInfo.isConnected
            }
        }
    }
