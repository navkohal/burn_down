package com.navdeep.burn_down

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.webkit.WebView
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog

class IntroductionScreen : AppCompatActivity() {

    var nxtBtn: Button? = null
    var termsTxt: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_introduction_screen)

        nxtBtn = findViewById(R.id.next_btn)
        termsTxt = findViewById(R.id.termsButton)


        nxtBtn?.setOnClickListener {
            val intent = Intent(this, GetUserDataScreen::class.java)
            startActivity(intent,  Utility.nextScreen(this).toBundle())
        }


        termsTxt?.setOnClickListener {
            showInformationDialogWithWebView(this)
        }
    }

    fun showInformationDialogWithWebView(context: Context) {
        val dialogView = LayoutInflater.from(context).inflate(R.layout.terms_layout_dialog, null)

        val webView = dialogView.findViewById<WebView>(R.id.webView)
        val close_iv = dialogView.findViewById<ImageView>(R.id.close_iv)

        val builder = AlertDialog.Builder(context)
            .setView(dialogView)

        val dialog = builder.create()

        webView.settings.javaScriptEnabled = true
        webView.loadUrl("file:///android_asset/terms_and_conditions.html")

        close_iv.setOnClickListener {
            // Handle button click
            dialog.dismiss()
        }

        dialog.show()
    }
}