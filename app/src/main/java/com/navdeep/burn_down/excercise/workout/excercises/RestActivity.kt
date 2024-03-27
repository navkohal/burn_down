package com.navdeep.burn_down.excercise.workout.excercises

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.ProgressBar
import android.widget.TextView
import com.navdeep.burn_down.R
import com.navdeep.burn_down.Utility

class RestActivity : AppCompatActivity() {

    lateinit var countdown_timer: CountDownTimer
    var timerTv : TextView?= null
    var timerProgressBar: ProgressBar?= null
    var isNextSet = false;
    val REST_RESULT_FLAG = 786

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (intent != null) {
           isNextSet = intent.getBooleanExtra("isSetComplete",false)
        }
        setContentView(R.layout.activity_rest)
        timerTv = findViewById(R.id.timer_tv)
        timerProgressBar = findViewById(R.id.timer_progress)
        if (isNextSet) {
            startTimer(Utility.REST_TIME_AFTER_SET)
            timerProgressBar?.max = 45
        } else {
            startTimer(Utility.REST_TIME_AFTER_REP)
            timerProgressBar?.max = 30
        }
    }

    private fun startTimer(timer: Long) {
        countdown_timer = object : CountDownTimer(timer, 1000) {
            override fun onFinish() {
                countdown_timer.cancel()
                setResult(REST_RESULT_FLAG)
                finish()
            }

            override fun onTick(p0: Long) {
                var long = (p0/1000)
                timerTv?.text = "$long" + "\nSEC"
                timerProgressBar?.setProgress(long.toInt())
            }
        }
        countdown_timer.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        countdown_timer.cancel()
    }

    override fun onPause() {
        super.onPause()
        countdown_timer.cancel()
    }
}