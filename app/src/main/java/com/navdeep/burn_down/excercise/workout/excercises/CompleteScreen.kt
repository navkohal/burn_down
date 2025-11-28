package com.navdeep.burn_down.excercise.workout.excercises

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.navdeep.burn_down.R
import com.navdeep.burn_down.Utility
import com.navdeep.burn_down.db.DatabaseService
import com.navdeep.burn_down.db.FavoriteDataClass
import com.navdeep.burn_down.excercise.ExcerciseMainScreen
import com.navdeep.burn_down.excercise.camera.CameraActivity
import com.navdeep.burn_down.excercise.favorite.FavoriteWorkoutActivity
import com.navdeep.burn_down.model.BaseResponse
import java.text.SimpleDateFormat
import java.util.*


class CompleteScreen : AppCompatActivity() {

    var workoutNameTv : TextView ?= null
    var gotoHomeBtn : CardView ?= null
    var camera_btn : CardView ?= null
    var favorite_btn : ImageView ?= null
    var unfavorite_btn : ImageView ?= null
    var workoutName = ""
    var workoutList: ArrayList<BaseResponse> = arrayListOf()
    var completionDate = ""
    var bitmap : Bitmap ?= null
    var isFavorite = false;

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
            val intent = Intent(this, ExcerciseMainScreen::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            }

            startActivity(intent, Utility.nextScreen(this).toBundle())
            finish()
        }

        camera_btn?.setOnClickListener {
            startActivity(Intent(this@CompleteScreen , CameraActivity :: class.java))
//            startActivity(Intent(this, FavoriteWorkoutActivity :: class.java),  Utility.nextScreen(this).toBundle())
//            finish()
        }


        unfavorite_btn?.setOnClickListener {
            isFavorite = false
            favorite_btn?.visibility = View.VISIBLE
            unfavorite_btn?.visibility = View.GONE
            database.deleteFavoriteWorkout(workoutName)
        }

        favorite_btn?.setOnClickListener {
                isFavorite = true
                favorite_btn?.visibility = View.GONE
                unfavorite_btn?.visibility = View.VISIBLE
                var favoriteModelClass = FavoriteDataClass(0,completionDate,workoutList, workoutName)
                database.insertFavoriteWorkout(favoriteModelClass)
        }
    }

    private fun checkIfFavorite() {
        val workoutKey = workoutName // using workoutName as key
        isFavorite = database.isWorkoutFavorite(workoutKey)

        if (isFavorite) {
            favorite_btn?.visibility = View.GONE
            unfavorite_btn?.visibility = View.VISIBLE
        } else {
            favorite_btn?.visibility = View.VISIBLE
            unfavorite_btn?.visibility = View.GONE
        }
    }

    private fun setData() {
        workoutNameTv?.text = workoutName + "\non" + "\n"+completionDate
        checkIfFavorite()
    }

    private fun initializeView() {
        workoutNameTv = findViewById(R.id.workout_title_tv)
        gotoHomeBtn = findViewById(R.id.goto_home_btn)
        camera_btn = findViewById(R.id.camera_btn)
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