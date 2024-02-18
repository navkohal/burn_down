package com.navdeep.burn_down

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import android.widget.ImageView
import com.navdeep.burn_down.dashboard.Dashboard

class ContactUsActivity : AppCompatActivity() {

    var backBtn  : ImageView ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact_us)

        backBtn = findViewById(R.id.back_btn)

        backBtn?.setOnClickListener {
            onBackPressed()
        }

        val webView: WebView = findViewById(R.id.webView)
        webView.settings.javaScriptEnabled = true
        webView.loadUrl("file:///android_asset/contact_us.html")
    }

    override fun onBackPressed() {
        finish()
    }
}