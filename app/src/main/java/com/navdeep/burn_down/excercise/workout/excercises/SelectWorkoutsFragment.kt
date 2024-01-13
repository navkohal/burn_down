package com.navdeep.burn_down.excercise.workout.excercises

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridView
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import com.google.gson.Gson
import com.navdeep.burn_down.R
import com.navdeep.burn_down.dashboard.model.BaseWorkoutModelClass
import com.navdeep.burn_down.model.BaseResponse
import com.navdeep.burn_down.network.NetworkClass.readJSONFromAssets
import java.io.Serializable


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SelectWorkoutsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SelectWorkoutsFragment : Fragment() , ChooseExcerciseAdapter.OnSelect {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var mContext: Context?= null
    var convertedObject : BaseWorkoutModelClass ?= null
    var mView : View ?= null
    var gridview: GridView ?= null
    var nextBtn: CardView ?= null
    var customAdapter : ChooseExcerciseAdapter ?= null
    var list: ArrayList<BaseResponse> = arrayListOf()
    var selectedList: ArrayList<BaseResponse> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        mContext = activity?.applicationContext
        getData();
    }

    private fun getData() {
        val string = readJSONFromAssets(mContext!!, "cardio.json")
        convertedObject = Gson().fromJson(string, BaseWorkoutModelClass::class.java)
        Log.d("TAG", "getData: "+string)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mView =  inflater.inflate(R.layout.fragment_select_workouts, container, false)
        nextBtn = mView!!.findViewById(R.id.next_btn) as CardView
        addData(convertedObject!!.response!!)
        showExcercisesList(list!!)
        setClickListeners()
        return mView
    }

    private fun setClickListeners() {
        nextBtn!!.setOnClickListener {
            val intent = Intent(context, StartExcerciseActivity :: class.java)
            intent.putExtra("excerciseData",selectedList)
            startActivity(intent)
        }
    }

    private fun addData(response: ArrayList<BaseResponse>) {
        list!!.addAll(response)
    }

    private fun showExcercisesList(list: ArrayList<BaseResponse>?) {
        customAdapter = ChooseExcerciseAdapter(mContext!!,list, this@SelectWorkoutsFragment)

        gridview = mView!!.findViewById(R.id.gridview)
        gridview?.adapter = customAdapter
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SelectWorkoutsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SelectWorkoutsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
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