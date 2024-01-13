package com.navdeep.burn_down.dashboard

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.navdeep.burn_down.R

class Dashboard : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        intializeAdapter()
    }

    private fun intializeAdapter() {
        val dataset = arrayOf(R.drawable.workout_plans,R.drawable.yoga_plans,
                                R.drawable.nutrition_images,R.drawable.bmi_calculator)
        val customAdapter = ListviewAdapter(this, dataset)

        val recyclerView: RecyclerView = findViewById(R.id.listview)
        recyclerView.setLayoutManager(LinearLayoutManager(this));
        recyclerView.adapter = customAdapter
    }
}