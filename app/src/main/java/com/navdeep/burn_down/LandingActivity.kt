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
            showSetupDataDialog()
           
        }

        subscriptionView?.setOnClickListener {

        }
    }

    private fun showSetupDataDialog() {
        if (database!!.getAllProfile() != null) {
            val intent = Intent(this, Dashboard::class.java)
            startActivity(intent,  Utility.nextScreen(this).toBundle())
        } else {
            val intent = Intent(this, GetUserDataScreen::class.java)
            startActivity(intent,  Utility.nextScreen(this).toBundle())
        }
    }
}