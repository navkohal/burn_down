package com.navdeep.burn_down.dashboard

import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.navdeep.burn_down.ContactUsActivity
import com.navdeep.burn_down.R
import com.navdeep.burn_down.Utility
import com.navdeep.burn_down.db.DatabaseService
import com.navdeep.burn_down.db.ProfileDataClass
import com.navdeep.burn_down.excercise.ExcerciseMainScreen
import com.navdeep.burn_down.excercise.favorite.FavoriteWorkoutActivity


class Dashboard : AppCompatActivity() , ListviewAdapter.OnSelect{

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
    lateinit var database: DatabaseService
    var profileDataClass : ProfileDataClass ?= null
    var name_tv : TextView ?= null
    var body_ms_tv : TextView ?= null
    var name_char_tv : TextView ?= null
    var firstChar : Char ?= null
    var nextScreenAnimation = Bundle()
    var previousScreen = Bundle()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        database = DatabaseService(this)
        profileDataClass = database.getAllProfile()

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
            startActivity(intent , nextScreenAnimation)
        }

        contactItem?.setOnClickListener {
            slideoutView()
            startActivity(Intent(this , ContactUsActivity :: class.java),nextScreenAnimation)
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
        body_ms_tv = findViewById(R.id.body_ms_tv)
        name_tv = findViewById(R.id.name_tv)
        name_char_tv = findViewById(R.id.name_char_tv)

        side_view_rl?.visibility = View.GONE

        setData()

        nextScreenAnimation = Utility.nextScreen(this).toBundle()

    }

    private fun setData() {
        if (profileDataClass != null) {
            name_tv!!.setText(profileDataClass!!.userName)
            body_ms_tv!!.setText(profileDataClass!!.height + " || "+profileDataClass!!.weight)
            firstChar = profileDataClass!!.userName.toString().first()
            name_char_tv!!.setText(firstChar.toString())
        }
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
        val customAdapter = ListviewAdapter(this, itemHeight / 4, dataset , this@Dashboard)

        recyclerView?.setLayoutManager(LinearLayoutManager(this));
        recyclerView?.adapter = customAdapter
    }

    override fun onItemSelect(position: Int) {

        when (position) {
            0 -> {
                startActivity(Intent(this , ExcerciseMainScreen :: class.java), nextScreenAnimation)
            }
            1 -> Toast.makeText(this, "Yoga Plan will be launched soon", Toast.LENGTH_SHORT).show()
            2 -> Toast.makeText(this, "Nutrition Plan will be launched soon", Toast.LENGTH_SHORT).show()
            3 -> Toast.makeText(this, "BMI calculator will be launched soon", Toast.LENGTH_SHORT).show()
//            1 ->  mContext.startActivity(Intent(mContext , YogaMainScreen :: class.java), nextScreenAnimation)
//            2 ->  mContext.startActivity(Intent(mContext , NutritionMainScreen :: class.java), nextScreenAnimation)
//            3 ->  mContext.startActivity(Intent(mContext , CalculateBmiScreen :: class.java), nextScreenAnimation)
        }
    }

}