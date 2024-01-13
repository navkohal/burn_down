package com.navdeep.burn_down.excercise.workout

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.navdeep.burn_down.R
import com.navdeep.burn_down.excercise.workout.excercises.SelectWorkoutsFragment

class WorkoutScreen : AppCompatActivity() {

    private val fragmentManager = supportFragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_workout_screen)

        setInitialview(this)
    }

    private fun setInitialview(mContext: Context) {
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.add(R.id.frameLayout, SelectWorkoutsFragment())
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }
}