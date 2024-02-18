package com.navdeep.burn_down

import android.app.ActivityOptions
import android.content.Context

object Utility {

    val REST_TIME_AFTER_SET = 45000L
    val REST_TIME_AFTER_REP = 30000L

    fun nextScreen(context : Context) : ActivityOptions{
        return ActivityOptions.makeCustomAnimation(context, R.anim.anim_in, 0)
    }

    fun lastScreen(context : Context) : ActivityOptions {
        return ActivityOptions.makeCustomAnimation(context, 0, R.anim.anim_out)
    }
}