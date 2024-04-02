package com.navdeep.burn_down

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class SubscriptionBroadcast : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == Utility.ACTION_SUBSCRIPTION_PURCHASED) {
            // Handle the subscription purchased event here
            // For example, show a message to the user
            Toast.makeText(context, "Subscription purchased!", Toast.LENGTH_SHORT).show()
        }
    }
}
