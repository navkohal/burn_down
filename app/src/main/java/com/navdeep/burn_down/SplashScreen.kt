package com.navdeep.burn_down

import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.*


class SplashScreen : AppCompatActivity() {
    var videoview : VideoView ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        videoview = findViewById(R.id.video_view);

        playVideo();
    }

    private fun playVideo() {
        val video: Uri = Uri.parse("android.resource://" + packageName + "/" + R.raw.splash_video)
        videoview?.setVideoURI(video)
        videoview?.seekTo(1000);

        videoview?.setOnCompletionListener(MediaPlayer.OnCompletionListener {
            startActivity(Intent(this, LandingActivity::class.java), Utility.nextScreen(this).toBundle())
            finish()
        })

        videoview?.setOnClickListener {
            startActivity(Intent(this, LandingActivity::class.java),  Utility.nextScreen(this).toBundle())
            finishAffinity()
        }

        videoview?.start()
    }
}