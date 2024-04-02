package com.navdeep.burn_down

import android.app.ActivityOptions
import android.content.Context
import android.net.ConnectivityManager

object Utility {

    val REST_TIME_AFTER_SET = 45000L
    val REST_TIME_AFTER_REP = 30000L
    const val ACTION_SUBSCRIPTION_PURCHASED = "com.navdeep.burn_down.SUBSCRIPTION_PURCHASED"


    fun nextScreen(context : Context) : ActivityOptions{
        return ActivityOptions.makeCustomAnimation(context, R.anim.anim_in, 0)
    }

    fun lastScreen(context : Context) : ActivityOptions {
        return ActivityOptions.makeCustomAnimation(context, 0, R.anim.anim_out)
    }

    public fun isInternetAvailable(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo?.isConnected ?: false
    }


}