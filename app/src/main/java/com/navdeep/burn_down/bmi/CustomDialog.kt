package com.navdeep.burn_down.bmi

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.navdeep.burn_down.R
import com.navdeep.burn_down.dashboard.Dashboard

class CustomDialog(context: Context, private val height: Double,private val weight: Double) : Dialog(context) {

    var bmi : Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.custom_dialog)

        calculateBMI(height , weight)

        val result_desc = findViewById<TextView>(R.id.result_desc)
        val view_1 = findViewById<ImageView>(R.id.view_1)
        val view_2 = findViewById<ImageView>(R.id.view_2)
        val view_3 = findViewById<ImageView>(R.id.view_3)
        val view_4 = findViewById<ImageView>(R.id.view_4)
        val done_btn = findViewById<Button>(R.id.done_btn)
        val re_btn = findViewById<Button>(R.id.re_btn)

        var result = bmi.toString().substring(0, 5)
        result_desc.text = "Your BMI is\n"+result

        if (bmi < 18.5) {
            view_1.visibility = View.VISIBLE
            view_2.visibility = View.INVISIBLE
            view_3.visibility = View.INVISIBLE
            view_4.visibility = View.INVISIBLE
        } else if (bmi >= 18.5 && bmi <= 24.9) {
            view_1.visibility = View.INVISIBLE
            view_2.visibility = View.VISIBLE
            view_3.visibility = View.INVISIBLE
            view_4.visibility = View.INVISIBLE
        } else if (bmi >= 25.0 && bmi <= 29.9) {
            view_1.visibility = View.INVISIBLE
            view_2.visibility = View.INVISIBLE
            view_3.visibility = View.VISIBLE
            view_4.visibility = View.INVISIBLE
        } else {
            view_1.visibility = View.INVISIBLE
            view_2.visibility = View.INVISIBLE
            view_3.visibility = View.INVISIBLE
            view_4.visibility = View.VISIBLE
        }


        done_btn.setOnClickListener {
            dismiss()
        }

        re_btn.setOnClickListener {
            dismiss()
        }
    }

    private fun calculateBMI(height: Double, weight: Double) {
        bmi = weight / (height * height)
    }
}
