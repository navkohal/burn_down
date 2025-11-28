package com.navdeep.burn_down.excercise

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.navdeep.burn_down.R
import com.navdeep.burn_down.Utility
import com.navdeep.burn_down.dashboard.Dashboard

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
            val intent = Intent(this, Dashboard::class.java)
            startActivity(intent, Utility.lastScreen(this).toBundle())
            finish()
        }

        setListViewData();
    }

    private fun setListViewData() {
        if ((!Utility.getFromSharedPreferences(this, "isInAppProductPurchased"))
            || (!Utility.getFromSharedPreferences(this, "isActiveSubscription"))) {
            val customAdapter = CategoryListviewAdapter(this, dataset,titles, false)
            val recyclerView: RecyclerView = findViewById(R.id.category_list)
            recyclerView.setLayoutManager(LinearLayoutManager(this));
            recyclerView.adapter = customAdapter
        } else {
            val customAdapter = CategoryListviewAdapter(this, dataset,titles , true)
            val recyclerView: RecyclerView = findViewById(R.id.category_list)
            recyclerView.setLayoutManager(LinearLayoutManager(this));
            recyclerView.adapter = customAdapter
        }
    }
}