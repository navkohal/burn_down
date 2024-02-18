package com.navdeep.burn_down.excercise.workout.excercises

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.bumptech.glide.Glide
import com.navdeep.burn_down.R
import com.navdeep.burn_down.Utility
import com.navdeep.burn_down.model.BaseResponse


class StartExcerciseActivity : AppCompatActivity() {

    var selectedList: ArrayList<BaseResponse> = arrayListOf()

    var backbtn: ImageView? = null
    var nextBtn: CardView? = null
    var headingTitle: TextView? = null
    var imagView: ImageView? = null
    var title: TextView? = null
    var eqp_title: TextView? = null
    var timerTv: TextView? = null
    var repsTv: TextView? = null
    var timerProgressBar: ProgressBar? = null
    var repsProgressBar: ProgressBar? = null
    lateinit var countdown_timer: CountDownTimer
    var excerciseCount = 0
    var totalExcerciseCount = 0
    var repsCount = 1
    var isLastExcercise = false
    val REST_RESULT_FLAG = 786

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_excercise_layout)

        if (intent.extras != null) {
            selectedList = intent.getSerializableExtra("excerciseData") as ArrayList<BaseResponse>
//            totalExcerciseCount = selectedList.size-1
            totalExcerciseCount = 1
        }

        setView()
        setData()
    }

    private fun setView() {
        backbtn = findViewById(R.id.back_btn)
        nextBtn = findViewById(R.id.next_btn)
        headingTitle = findViewById(R.id.heading_tv)
        imagView = findViewById(R.id.imageview)
        title = findViewById(R.id.name_tv)
        eqp_title = findViewById(R.id.equipment_title)
        timerProgressBar = findViewById(R.id.timer_progress)
        timerTv = findViewById(R.id.timer_tv)
        repsProgressBar = findViewById(R.id.reps_progress)
        repsTv = findViewById(R.id.reps_tv)

        backbtn?.setOnClickListener {
            finish()
        }

        nextBtn?.setOnClickListener {
            if (countdown_timer != null) {
                countdown_timer.cancel()
            }
            showRestBetweenReps()
        }
    }

    private fun setData() {
        headingTitle?.setText(selectedList.get(excerciseCount).bodyPart)
        title?.setText(selectedList.get(excerciseCount).name)
        eqp_title?.setText(selectedList.get(excerciseCount).equipment)

        val res = resources.getIdentifier(
            selectedList.get(excerciseCount).gifUrl,
            "drawable",
            this.packageName
        )

        Glide.with(this)
            .load(res)
            .into(imagView!!)

        startTimer(3000)
        repsTv?.text = "$repsCount" + "/3\nREPS"
    }

    private fun startTimer(timer: Long) {
        countdown_timer = object : CountDownTimer(timer, 1000) {
            override fun onFinish() {
                countdown_timer.cancel()
                showRestBetweenReps()
            }

            override fun onTick(p0: Long) {
                var long = (p0 / 1000)
                timerTv?.text = "$long" + "\nSEC"
                timerProgressBar?.setProgress(long.toInt())
            }
        }
        countdown_timer.start()
    }

    private fun showRestBetweenReps() {
        val intent = Intent(this, RestActivity::class.java)
        if (repsCount < 3) {
            intent.putExtra("isSetComplete", false)
        } else {
            if (isLastExcercise) {
                showCompleteExerciseScreen()
                return
            }
            intent.putExtra("isSetComplete", true)
        }
        startActivityForResult(intent, REST_RESULT_FLAG ,  Utility.nextScreen(this).toBundle())
    }

    private fun showCompleteExerciseScreen() {
        var intent = Intent(this, CompleteScreen::class.java)
        intent.putExtra("workoutname",selectedList.get(0).bodyPart)
        intent.putExtra("excerciseData",selectedList)
        startActivity(intent,  Utility.nextScreen(this).toBundle())
        finish()
    }


    override fun onPause() {
        super.onPause()
        countdown_timer.cancel()
    }

    override fun onResume() {
        super.onResume()
        countdown_timer.start()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REST_RESULT_FLAG) {
            // Make sure the request was successful
            if (repsCount == 3) {
                repsCount = 1

                if (excerciseCount < totalExcerciseCount) {
                    excerciseCount = excerciseCount + 1
                    setData()
                } else {
                    //finish workout
                    showCompleteExerciseScreen()
                }
            } else {
                // check if it is last exercise
                checkIfLastExercise()

                // increase reps count and repeat exercise
                repsCount = repsCount + 1
                setData()
            }

        }
    }

    private fun checkIfLastExercise() {
        if (excerciseCount == totalExcerciseCount) {
            isLastExcercise = true
        } else {
            isLastExcercise = false
        }
    }

}
