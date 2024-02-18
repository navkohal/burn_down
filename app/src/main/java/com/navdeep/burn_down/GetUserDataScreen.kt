package com.navdeep.burn_down

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.SeekBar
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.navdeep.burn_down.dashboard.Dashboard
import com.navdeep.burn_down.db.DatabaseService
import com.navdeep.burn_down.db.FavoriteDataClass
import com.navdeep.burn_down.db.ProfileDataClass

class GetUserDataScreen : AppCompatActivity() {
    
    var nextBtn : CardView ?= null
    var nameEdtTxt : EditText ?= null
    var weightSeekbar : SeekBar ?= null
    var weightValueTv : TextView ?= null
    var heightSeekBar : SeekBar ?= null
    var heightValueTv : TextView ?= null

    lateinit var database: DatabaseService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_get_user_data_screen)
        database = DatabaseService(this)

        initializeView()

        setClickOperations()
        
    }

    private fun setClickOperations() {
        weightSeekbar?.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                weightValueTv?.setText(""+progress + "lbs")
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                // Called when tracking starts
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                // Called when tracking stops
            }
        })

        heightSeekBar?.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                heightValueTv?.setText(""+progress + "cm")
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                // Called when tracking starts
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                // Called when tracking stops
            }
        })

        nextBtn?.setOnClickListener {
            if (! nameEdtTxt!!.text!!.isEmpty()) {

                saveProfileData()

                startActivity(Intent(this, Dashboard::class.java),  Utility.nextScreen(this).toBundle())
                finish()
            }
        }

    }

    private fun saveProfileData() {
        var profileDataClass = ProfileDataClass(0,nameEdtTxt!!.text.toString(), weightValueTv!!.text.toString()
                                            , heightValueTv!!.text.toString())
        database.insertProfileData(profileDataClass)
    }

    private fun initializeView() {
        nextBtn = findViewById(R.id.next_btn)
        nameEdtTxt = findViewById(R.id.name_edt)
        weightSeekbar = findViewById(R.id.seekBar_weight)
        heightSeekBar = findViewById(R.id.seekBar_height)
        weightValueTv = findViewById(R.id.weight_value_tv)
        heightValueTv = findViewById(R.id.heigth_value_tv)
    }
}