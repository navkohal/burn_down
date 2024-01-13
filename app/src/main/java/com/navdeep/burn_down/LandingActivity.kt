package com.navdeep.burn_down

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.navdeep.burn_down.dashboard.Dashboard

class LandingActivity : AppCompatActivity() {

    var trialView : View?= null;
    var subscriptionView : View?= null;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_landing)
        setClickListeners()
    }

    private fun setClickListeners() {
        trialView = findViewById(R.id.trial_view)
        subscriptionView = findViewById(R.id.subscription_view)

        trialView?.setOnClickListener {
            startActivity(Intent(this, Dashboard::class.java))
            finish()
        }

        subscriptionView?.setOnClickListener {

        }
    }
}