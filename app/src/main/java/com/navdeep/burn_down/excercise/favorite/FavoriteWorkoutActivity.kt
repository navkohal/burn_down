package com.navdeep.burn_down.excercise.favorite

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.navdeep.burn_down.R
import com.navdeep.burn_down.Utility
import com.navdeep.burn_down.dashboard.Dashboard
import com.navdeep.burn_down.db.DatabaseService
import com.navdeep.burn_down.db.FavoriteDataClass
import com.navdeep.burn_down.excercise.workout.WorkoutScreen
import com.navdeep.burn_down.excercise.workout.excercises.StartExcerciseActivity
import com.navdeep.burn_down.model.BaseResponse

class FavoriteWorkoutActivity : AppCompatActivity() {

    lateinit var database: DatabaseService
    var fav_list: ArrayList<FavoriteDataClass> = arrayListOf()
    var backBtn: ImageView? = null
    var screenName = ""
    var placeHolder_tv: TextView ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite_workout)

        if (intent.extras != null) {
            screenName = intent.getStringExtra("screen")!!
        }

        database = DatabaseService(this)
        fav_list = database.getAllFavorites()!! as ArrayList<FavoriteDataClass>
        placeHolder_tv = findViewById(R.id.placeholder_tv)

        setListViewData(fav_list)
        setOperations()
    }

    private fun setOperations() {
        backBtn = findViewById(R.id.back_btn)
        backBtn?.setOnClickListener {
            backScreen()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        backScreen()
    }

    private fun backScreen() {
        if (!TextUtils.isEmpty("Excercise_list")) {
            finish()
        } else {
            startActivity(Intent(this, Dashboard::class.java),  Utility.nextScreen(this).toBundle())
            finish()
        }
    }

    override fun onResume() {
        super.onResume()
        fav_list = database.getAllFavorites()!! as ArrayList<FavoriteDataClass>
    }

    private fun setListViewData(favList: ArrayList<FavoriteDataClass>) {
        if (favList.size == 0) {
            placeHolder_tv?.setVisibility(View.VISIBLE)
            return
        }
        
        val customAdapter = FavoriteWorkoutAdapter(
            this,
            favList,
            object : FavoriteWorkoutAdapter.OnFavoriteClickListener {
                override fun onFavoriteClicked(
                    item: List<BaseResponse>,
                ) {
                   startYourFavoriteWorkout(item)
                }
            }
        )

        val recyclerView: RecyclerView = findViewById(R.id.fav_list)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = customAdapter
    }

    private fun startYourFavoriteWorkout(item: List<BaseResponse>) {
        val intent = Intent(this@FavoriteWorkoutActivity, StartExcerciseActivity::class.java)

        // Convert to ArrayList
        intent.putExtra("excerciseData", ArrayList(item)) // now it's Serializable

        startActivity(intent, Utility.nextScreen(this@FavoriteWorkoutActivity).toBundle())
    }

}