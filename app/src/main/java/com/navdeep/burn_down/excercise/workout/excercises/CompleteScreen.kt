package com.navdeep.burn_down.excercise.workout.excercises

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Rect
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.MediaStore
import android.util.Log
import android.view.PixelCopy
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.navdeep.burn_down.CameraActivity
import com.navdeep.burn_down.R
import com.navdeep.burn_down.Utility
import com.navdeep.burn_down.db.DatabaseService
import com.navdeep.burn_down.db.FavoriteDataClass
import com.navdeep.burn_down.excercise.ExcerciseMainScreen
import com.navdeep.burn_down.model.BaseResponse
import java.io.ByteArrayOutputStream
import java.text.SimpleDateFormat
import java.util.*


class CompleteScreen : AppCompatActivity() {

    var workoutNameTv : TextView ?= null
    var gotoHomeBtn : CardView ?= null
    var goto_favorite : CardView ?= null
    var favorite_btn : ImageView ?= null
    var unfavorite_btn : ImageView ?= null
    var workoutName = ""
    var workoutList: ArrayList<BaseResponse> = arrayListOf()
    var completionDate = ""
    var bitmap : Bitmap ?= null

    lateinit var database: DatabaseService


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.navdeep.burn_down.R.layout.activity_complete_screen)
        if (intent.extras != null) {
            workoutName = intent.getStringExtra("workoutname")!!
            workoutList = intent.getSerializableExtra("excerciseData") as ArrayList<BaseResponse>
        }
        database = DatabaseService(this)

        getTodaysDate()
        initializeView()
        setData()
        setClickListeners()
    }

    private fun setClickListeners() {
        gotoHomeBtn?.setOnClickListener {
            startActivity(Intent(this, ExcerciseMainScreen :: class.java),  Utility.nextScreen(this).toBundle())
            finish()
        }

        goto_favorite?.setOnClickListener {
//            startActivity(Intent(this, FavoriteWorkoutActivity :: class.java),  Utility.nextScreen(this).toBundle())
//            finish()
            // Call getScreenBitmap method
           /* getScreenBitmap(this) { bitmap ->
                if (bitmap != null) {
                    // Use the bitmap, for example, share it on Instagram
                    shareOnInstagram(bitmap)
                } else {
                    // Handle the case where bitmap is null
                }
            }*/

            startActivity(Intent(this, CameraActivity :: class.java),  Utility.nextScreen(this).toBundle())
        }
        var isFavorite = false;
        favorite_btn?.setOnClickListener {
            if (!isFavorite) {
                isFavorite = true
                favorite_btn?.visibility = View.GONE
                unfavorite_btn?.visibility = View.VISIBLE
                var favoriteModelClass = FavoriteDataClass(0,completionDate,workoutList)
                database.insertFavoriteWorkout(favoriteModelClass)
            } else {
                isFavorite = false
//                favorite_btn!!.setBackgroundResource(R.drawable.favorite) //here unfavorite is fullfiled color star icon
            }
        }
    }

    private fun shareOnInstagram(bitmap: Bitmap) {
        // Assuming 'bitmap' is the bitmap of the screen view you want to share
        val uri = getImageUri(this, bitmap)
        val shareIntent = Intent(Intent.ACTION_SEND).apply {
            type = "image/*"
            putExtra(Intent.EXTRA_STREAM, uri)
            setPackage("com.instagram.android") // Package name for Instagram
        }
        try {
            startActivity(shareIntent)
        } catch (e: ActivityNotFoundException) {
            // Instagram not installed, handle error here
        }
    }

    private fun getImageUri(context: Context, bitmap: Bitmap): Uri {
        val bytes = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, bytes)
        val path = MediaStore.Images.Media.insertImage(context.contentResolver, bitmap, "Title", null)
        return Uri.parse(path)
    }

    fun getScreenBitmap(activity: Activity, callback: (Bitmap?) -> Unit) {
        val view = activity.window.decorView
        bitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
        val location = IntArray(2)
        view.getLocationInWindow(location)

        try {
            PixelCopy.request(
                activity.window,
                Rect(location[0], location[1], location[0] + view.width, location[1] + view.height),
                bitmap!!,
                { copyResult ->
                    if (copyResult == PixelCopy.SUCCESS) {
                        callback(bitmap)
                    } else {
                        callback(null)
                    }
                },
                Handler(Looper.getMainLooper())
            )
        } catch (e: IllegalArgumentException) {
            // PixelCopy may throw IllegalArgumentException, handle it here
            callback(null)
        }
    }

    private fun setData() {
        workoutNameTv?.text = workoutName + "\non" + "\n"+completionDate
    }

    private fun initializeView() {
        workoutNameTv = findViewById(R.id.workout_title_tv)
        gotoHomeBtn = findViewById(R.id.goto_home_btn)
        goto_favorite = findViewById(R.id.goto_favorite)
        favorite_btn = findViewById(R.id.favorite_btn)
        unfavorite_btn = findViewById(R.id.unfavorite_btn)
    }

    private fun getTodaysDate() {
        val currentDate = Date()
        val dateFormat = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
        completionDate = dateFormat.format(currentDate)
        Log.d("Completed Date", completionDate)
    }
}