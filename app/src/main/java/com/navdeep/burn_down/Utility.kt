package com.navdeep.burn_down

import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.navdeep.burn_down.dashboard.Dashboard
import java.time.LocalDate
import java.time.format.DateTimeFormatter

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

    fun getCurrentDate(): String {
        val currentDate = LocalDate.now()
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        return currentDate.format(formatter)
    }

    fun isTrialPeriodAvailable(date1: String, date2: String): Boolean {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val localDate1 = LocalDate.parse(date1, formatter)
        val localDate2 = LocalDate.parse(date2, formatter)
        var diff = localDate1.compareTo(localDate2)
        if (diff > 30 || diff < 0) {
            return false
        }
        return true
    }

    fun showSubscriptionDialog(mContext: Context, message: String, screenName: String) {
        val dialogView = LayoutInflater.from(mContext).inflate(R.layout.trial_over_layout, null)

        val close_iv = dialogView.findViewById<ImageView>(R.id.closeButton)
        val okBtn = dialogView.findViewById<Button>(R.id.submitButton)
        val messagetv = dialogView.findViewById<TextView>(R.id.messageTextView)

        val builder = AlertDialog.Builder(mContext)
            .setCancelable(false)
            .setView(dialogView)

        val dialog = builder.create()
        messagetv.text = message

        if (screenName.equals("HOME")) {
            close_iv.visibility = View.INVISIBLE
        }

        close_iv.setOnClickListener {
            // Handle button click
            dialog.dismiss()
        }

        okBtn.setOnClickListener {
            dialog.dismiss()
            if (screenName.equals("HOME")) {
                //do nothing
            }else {
                mContext.startActivity(Intent(mContext , SubscriptionScreen :: class.java))
            }
        }

        dialog.show()
    }


    // Function to save a string value to SharedPreferences
    fun saveToSharedPreferences(context: Context, key: String, value: Boolean) {
        val sharedPreferences = context.getSharedPreferences("MySharedPreferences", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean(key, value)
        editor.apply()
    }

    // Function to retrieve a string value from SharedPreferences
    fun getFromSharedPreferences(context: Context, key: String): Boolean {
        val sharedPreferences = context.getSharedPreferences("MySharedPreferences", Context.MODE_PRIVATE)
        return sharedPreferences.getBoolean(key, false)
    }


    fun isInternetConnected(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val network = connectivityManager.activeNetwork ?: return false
            val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
            return capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
        } else {
            val networkInfo = connectivityManager.activeNetworkInfo ?: return false
            return networkInfo.isConnected
        }
    }

}