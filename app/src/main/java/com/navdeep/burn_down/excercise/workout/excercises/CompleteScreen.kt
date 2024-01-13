package com.navdeep.burn_down.excercise.workout.excercises

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.navdeep.burn_down.R
import com.navdeep.burn_down.excercise.ExcerciseMainScreen

class CompleteScreen : AppCompatActivity() {

    var workoutNameTv : TextView ?= null
    var gotoHomeBtn : CardView ?= null
    var addToFavorite : CardView ?= null
    var workoutName = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_complete_screen)

        if (intent.extras != null) {
            workoutName = intent.getStringExtra("workoutname")!!
        }

        initializeView()
        setData()
        setClickListeners()
    }

    private fun setClickListeners() {
        gotoHomeBtn?.setOnClickListener {
            startActivity(Intent(this, ExcerciseMainScreen :: class.java))
            finish()
        }
    }

    private fun setData() {
        workoutNameTv?.text = workoutName + "\nCOMPLETED"
    }

    private fun initializeView() {
        workoutNameTv = findViewById(R.id.workout_title_tv)
        gotoHomeBtn = findViewById(R.id.goto_home_btn)
        addToFavorite = findViewById(R.id.favorite_btn)
    }
}