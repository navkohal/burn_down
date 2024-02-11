package com.navdeep.burn_down.excercise.workout

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.GridView
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.google.gson.Gson
import com.navdeep.burn_down.R
import com.navdeep.burn_down.dashboard.model.BaseWorkoutModelClass
import com.navdeep.burn_down.excercise.workout.excercises.ChooseExcerciseAdapter
import com.navdeep.burn_down.excercise.workout.excercises.StartExcerciseActivity
import com.navdeep.burn_down.model.BaseResponse
import com.navdeep.burn_down.network.NetworkClass

class WorkoutScreen : AppCompatActivity() , ChooseExcerciseAdapter.OnSelect {

    var fileName = "cardio.json"
    var convertedObject : BaseWorkoutModelClass?= null
    var mView : View?= null
    var gridview: GridView?= null
    var nextBtn: CardView?= null
    var backBtn: ImageView?= null
    var customAdapter : ChooseExcerciseAdapter ?= null
    var list: ArrayList<BaseResponse> = arrayListOf()
    var selectedList: ArrayList<BaseResponse> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getData()
        if (intent.extras != null) {
            fileName = intent.getStringExtra("file_name").toString()
        }

        setContentView(R.layout.activity_workout_screen)

        nextBtn = findViewById(R.id.next_btn) as CardView
        backBtn = findViewById(R.id.back_btn) as ImageView
        addData(convertedObject!!.response!!)
        showExcercisesList(list!!)
        setClickListeners()
    }

    private fun getData() {
        val string = NetworkClass.readJSONFromAssets(this, fileName)
        convertedObject = Gson().fromJson(string, BaseWorkoutModelClass::class.java)
        Log.d("TAG", "getData: "+string)
    }


    private fun setClickListeners() {
        nextBtn!!.setOnClickListener {
            val intent = Intent(this, StartExcerciseActivity :: class.java)
            intent.putExtra("excerciseData",selectedList)
            startActivity(intent)
        }

        backBtn?.setOnClickListener { finish() }
    }

    private fun addData(response: ArrayList<BaseResponse>) {
        list!!.addAll(response)
    }

    private fun showExcercisesList(list: ArrayList<BaseResponse>?) {
        customAdapter = ChooseExcerciseAdapter(this,list, this@WorkoutScreen)

        gridview = findViewById(R.id.gridview)
        gridview?.adapter = customAdapter
    }

    override fun onItemSelect(position: Int) {
        var baseResponse : BaseResponse ?= null
        if(!list?.get(position)?.isSelected!!) {
            baseResponse = BaseResponse(list?.get(position)!!.bodyPart, list?.get(position)!!.equipment,
                list?.get(position)!!.gifUrl, list?.get(position)!!.id, list?.get(position)!!.name,
                list?.get(position)!!.target,list?.get(position)!!.secondaryMuscles,list?.get(position)!!.instructions,isSelected = true)
        } else {
            baseResponse = BaseResponse(list?.get(position)!!.bodyPart, list?.get(position)!!.equipment,
                list?.get(position)!!.gifUrl, list?.get(position)!!.id, list?.get(position)!!.name,
                list?.get(position)!!.target,list?.get(position)!!.secondaryMuscles,list?.get(position)!!.instructions,isSelected = false)
        }
        list?.set(position, baseResponse)
        setSelectedList()
        customAdapter?.notifyDataSetChanged()

    }

    private fun setSelectedList() {
        var count = 0
        selectedList.clear()
        for ( i in 0..(list!!.size)-1) {
            if (list!!.get(i).isSelected) {
                selectedList.add(list!!.get(i))
                count = count+1
            }
        }
        if (count == 5) {
            nextBtn!!.visibility = View.VISIBLE
        } else {
            nextBtn!!.visibility = View.INVISIBLE
        }
    }
}