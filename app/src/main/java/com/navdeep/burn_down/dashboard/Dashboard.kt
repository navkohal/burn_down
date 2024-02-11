package com.navdeep.burn_down.dashboard

import android.content.Intent
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.TranslateAnimation
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.navdeep.burn_down.R
import com.navdeep.burn_down.excercise.favorite.FavoriteWorkoutActivity


class Dashboard : AppCompatActivity() {

    var itemHeight = 200

    var side_view_rl: RelativeLayout? = null
    var menu_iv: ImageView? = null
    var close_iv: ImageView? = null
    var recyclerView: RecyclerView? = null
    var homeItem: TextView? = null
    var favoriteItem: TextView? = null
    var contactItem: TextView? = null
    var subscription_item: TextView? = null
    var rootview: RelativeLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        initializeView()

        getDisplayMetrics()

        setClickListners()
    }

    private fun setClickListners() {
        menu_iv?.setOnClickListener {
            slideInView()
        }

        close_iv?.setOnClickListener {
            menu_iv?.visibility = View.VISIBLE
            slideoutView()
        }

        side_view_rl?.setOnClickListener {  }

        homeItem?.setOnClickListener {
            slideoutView()
        }

        favoriteItem?.setOnClickListener {
            slideoutView()
            var intent = Intent(this, FavoriteWorkoutActivity::class.java)
            intent.putExtra("screen","Excercise_list")
            startActivity(intent)
        }

        contactItem?.setOnClickListener {
            slideoutView()
        }

        subscription_item?.setOnClickListener {
            slideoutView()
        }

    }

    private fun slideoutView() {
        close_iv?.visibility = View.GONE
        side_view_rl?.visibility = View.GONE
        menu_iv?.visibility = View.VISIBLE

        val animation: Animation =
            AnimationUtils.loadAnimation(applicationContext, R.anim.anim_out)
            side_view_rl?.startAnimation(animation)
    }

    private fun slideInView() {
        close_iv?.visibility = View.VISIBLE
        side_view_rl?.visibility = View.VISIBLE
        menu_iv?.visibility = View.GONE

        val animation: Animation =
            AnimationUtils.loadAnimation(applicationContext,R.anim.side_view_out)
        side_view_rl?.startAnimation(animation)
    }

    private fun initializeView() {
        recyclerView = findViewById(R.id.listview)
        menu_iv = findViewById(R.id.menu_iv)
        close_iv = findViewById(R.id.close_iv)
        rootview = findViewById(R.id.rootview)
        side_view_rl = findViewById(R.id.side_view_rl)
        homeItem = findViewById(R.id.home_item)
        favoriteItem = findViewById(R.id.fav_item)
        contactItem = findViewById(R.id.contact_item)
        subscription_item = findViewById(R.id.subscription_item)

        side_view_rl?.visibility = View.GONE

    }

    private fun getDisplayMetrics() {
        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        itemHeight = displayMetrics.heightPixels

        intializeAdapter()
    }

    private fun intializeAdapter() {
        val dataset = arrayOf(
            R.drawable.workout_plans, R.drawable.yoga_plans,
            R.drawable.nutrition_images, R.drawable.bmi_calculator
        )
        val customAdapter = ListviewAdapter(this, itemHeight / 4, dataset)

        recyclerView?.setLayoutManager(LinearLayoutManager(this));
        recyclerView?.adapter = customAdapter
    }

}