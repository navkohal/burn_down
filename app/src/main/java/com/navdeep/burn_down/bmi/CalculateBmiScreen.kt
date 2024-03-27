package com.navdeep.burn_down.bmi

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import android.widget.SeekBar.OnSeekBarChangeListener
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import com.navdeep.burn_down.R

class CalculateBmiScreen : AppCompatActivity() {

    var mcurrentheight: TextView? = null
    var mcurrentweight: TextView? = null
    var mcurrentage:TextView? = null
    var mincrementage: ImageView? = null
    var mdecrementage:android.widget.ImageView? = null
    var mincrementweight:android.widget.ImageView? = null
    var mdecrementweight:android.widget.ImageView? = null
    var mseekbarforheight: SeekBar? = null
    var mcalculatebmi: CardView? = null
    var mmale: RelativeLayout? = null
    var mfemale:RelativeLayout? = null
    var backbtn: ImageView? = null

    var intweight = 150
    var intage = 22
    var currentprogress = 0
    var mintprogress = "170"
    var typerofuser = "0"
    var weight2 = "150"
    var age2 = "22"

    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calculate_bmi_screen)
        mcurrentage = findViewById<TextView>(R.id.currentage)
        mcurrentweight = findViewById<TextView>(R.id.currentweight)
        mcurrentheight = findViewById<TextView>(R.id.currentheight)
        mincrementage = findViewById<ImageView>(R.id.incrementage)
        mdecrementage = findViewById<ImageView>(R.id.decrementage)
        mincrementweight = findViewById<ImageView>(R.id.incremetweight)
        mdecrementweight = findViewById<ImageView>(R.id.decrementweight)
        mcalculatebmi = findViewById<CardView>(R.id.calculatebmi)
        mseekbarforheight = findViewById<SeekBar>(R.id.seekbarforheight)
        mmale = findViewById<RelativeLayout>(R.id.male)
        mfemale = findViewById<RelativeLayout>(R.id.female)
        backbtn = findViewById<ImageView>(R.id.back_btn)
        mmale?.setOnClickListener(View.OnClickListener {
            mmale?.setBackground(
                ContextCompat.getDrawable(
                    applicationContext,
                    R.drawable.malefemalefocus
                )
            )
            mfemale?.setBackground(
                ContextCompat.getDrawable(
                    applicationContext,
                    R.drawable.malefemalenotfocus
                )
            )
            typerofuser = "Male"
        })
        mfemale?.setOnClickListener(View.OnClickListener {
            mfemale?.setBackground(
                ContextCompat.getDrawable(
                    applicationContext,
                    R.drawable.malefemalefocus
                )
            )
            mmale?.setBackground(
                ContextCompat.getDrawable(
                    applicationContext,
                    R.drawable.malefemalenotfocus
                )
            )
            typerofuser = "Female"
        })

        backbtn?.setOnClickListener { finish() }

        mseekbarforheight?.setMax(300)
        mseekbarforheight?.setProgress(170)
        mseekbarforheight?.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                currentprogress = progress
                mintprogress = currentprogress.toString()
                mcurrentheight?.setText(mintprogress)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {}
            override fun onStopTrackingTouch(seekBar: SeekBar) {}
        })
        mincrementweight?.setOnClickListener(View.OnClickListener {
            intweight = intweight + 1
            weight2 = intweight.toString()
            mcurrentweight?.setText(weight2)
        })
        mincrementage?.setOnClickListener(View.OnClickListener {
            intage = intage + 1
            age2 = intage.toString()
            mcurrentage?.setText(age2)
        })
        mdecrementage?.setOnClickListener(View.OnClickListener {
            intage = intage - 1
            age2 = intage.toString()
            mcurrentage?.setText(age2)
        })
        mdecrementweight?.setOnClickListener(View.OnClickListener {
            intweight = intweight - 1
            weight2 = intweight.toString()
            mcurrentweight?.setText(weight2)
        })
        mcalculatebmi?.setOnClickListener(View.OnClickListener {
            if (typerofuser == "0") {
                Toast.makeText(applicationContext, "Select Your Gender First", Toast.LENGTH_SHORT)
                    .show()
            } else if (mintprogress == "0") {
                Toast.makeText(applicationContext, "Select Your Height First", Toast.LENGTH_SHORT)
                    .show()
            } else if (intage == 0 || intage < 0) {
                Toast.makeText(applicationContext, "Age is Incorrect", Toast.LENGTH_SHORT).show()
            } else if (intweight == 0 || intweight < 0) {
                Toast.makeText(applicationContext, "Weight Is Incorrect", Toast.LENGTH_SHORT).show()
            } else {
                showResultDialog()
            }
        })
    }

    private fun showResultDialog() {
        val customDialog = CustomDialog(this, mintprogress.toDouble()/100.0 , weight2.toDouble() * 0.45359237)
        customDialog.show()
    }

}