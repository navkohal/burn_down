package com.navdeep.burn_down.excercise

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.navdeep.burn_down.R

class ExcerciseMainScreen : AppCompatActivity() {

    val dataset = arrayOf(R.drawable.cardio,R.drawable.chest_workout,
        R.drawable.back_workout,R.drawable.shoulders_workout, R.drawable.arms_workout
        ,R.drawable.legs_workout, R.drawable.neck_workout)

    val titles = arrayOf("Cardio", "Chest", "Back", "Shoulders", "Arms", "Legs", "Neck")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_excercise_main_screen)


        setListViewData();
    }

    private fun setListViewData() {
        val customAdapter = CategoryListviewAdapter(this, dataset,titles)

        val recyclerView: RecyclerView = findViewById(R.id.category_list)
        recyclerView.setLayoutManager(LinearLayoutManager(this));
        recyclerView.adapter = customAdapter
    }
}