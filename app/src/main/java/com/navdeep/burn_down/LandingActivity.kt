package com.navdeep.burn_down

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.navdeep.burn_down.dashboard.Dashboard
import com.navdeep.burn_down.db.DatabaseService

class LandingActivity : AppCompatActivity() {

    var trialView : View?= null;
    var subscriptionView : View?= null;
    var database: DatabaseService?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_landing)
        database = DatabaseService(applicationContext)

        var mContext : Context = this

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