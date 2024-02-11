package com.navdeep.burn_down.excercise

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.navdeep.burn_down.R
import com.navdeep.burn_down.excercise.favorite.FavoriteWorkoutActivity
import com.navdeep.burn_down.excercise.workout.excercises.CompleteScreen

class ExcerciseMainScreen : AppCompatActivity() {

    val dataset = arrayOf(R.drawable.cardio,R.drawable.chest_workout,
        R.drawable.back_workout,R.drawable.shoulders_workout, R.drawable.arms_workout
        ,R.drawable.legs_workout, R.drawable.abs_bg)

    val titles = arrayOf("Cardio", "Back", "Chest", "Arms", "Shoulders", "Abs","Legs")

    var back_btn : ImageView ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_excercise_main_screen)


        back_btn = findViewById(R.id.back_btn)
        back_btn?.setOnClickListener {
            finish()
        }

        setListViewData();
    }

    private fun setListViewData() {
        val customAdapter = CategoryListviewAdapter(this, dataset,titles)

        val recyclerView: RecyclerView = findViewById(R.id.category_list)
        recyclerView.setLayoutManager(LinearLayoutManager(this));
        recyclerView.adapter = customAdapter
    }
}