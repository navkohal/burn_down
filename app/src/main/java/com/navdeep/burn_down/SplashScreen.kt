package com.navdeep.burn_down

import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import com.navdeep.burn_down.dashboard.Dashboard
import com.navdeep.burn_down.db.AppInstalledDate
import com.navdeep.burn_down.db.DatabaseService
import com.navdeep.burn_down.db.FavoriteDataClass
import java.io.File
import java.io.IOException
import java.net.URL
import java.text.SimpleDateFormat
import java.util.*


class SplashScreen : AppCompatActivity() {
    var videoview : VideoView ?= null
    var database: DatabaseService?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        database = DatabaseService(applicationContext)

        val dexOutputDir: File = codeCacheDir
        dexOutputDir.setReadOnly()
        setContentView(R.layout.activity_main)
        videoview = findViewById(R.id.video_view);


        playVideo();
        FetchDateTimeTask().execute()
    }

    private fun playVideo() {
        val video: Uri = Uri.parse("android.resource://" + packageName + "/" + R.raw.splash_video)
        videoview?.setVideoURI(video)
        videoview?.seekTo(1000);

        videoview?.setOnCompletionListener(MediaPlayer.OnCompletionListener {
//            startActivity(Intent(this, LandingActivity::class.java), Utility.nextScreen(this).toBundle())
            navigateToNextScreen()
        })

        videoview?.setOnClickListener {
//            startActivity(Intent(this, LandingActivity::class.java),  Utility.nextScreen(this).toBundle())
            navigateToNextScreen()
        }

        videoview?.start()
    }

    fun navigateToNextScreen() {
        if (database!!.getAllProfile() != null) {
            startActivity(Intent(this, Dashboard::class.java),  Utility.nextScreen(this).toBundle())
            finishAffinity()
        } else {
            startActivity(Intent(this, IntroductionScreen::class.java),  Utility.nextScreen(this).toBundle())
            finishAffinity()
        }
    }

    private inner class FetchDateTimeTask : AsyncTask<Void, Void, String>() {

        override fun doInBackground(vararg params: Void): String? {
            return try {
                val url = URL("https://worldtimeapi.org/api/ip")
                url.readText()
            } catch (e: IOException) {
                null
            }
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            if (result != null) {
                val dateTimeString = result.substringAfter("\"datetime\":\"").substringBefore("\",\"timezone\"")
                val dateOnlyString = getDateFromDateAndTimeString(dateTimeString)
                var appInstalledDate = AppInstalledDate(0,dateOnlyString)
                database?.insertAppInstalledDate(appInstalledDate)
                Log.d("TAG", "onMyPostExecute: "+dateOnlyString)
                // Use dateTimeString as needed
            } else {
                // Handle error
            }
        }

        fun getDateFromDateAndTimeString(dateTimeString: String): String {
            val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
            val date = dateFormat.parse(dateTimeString)
            val dateOnlyFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            return dateOnlyFormat.format(date)
        }
    }
}