package com.navdeep.burn_down.dashboard

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.navdeep.burn_down.R
import com.navdeep.burn_down.bmi.CalculateBmiScreen
import com.navdeep.burn_down.excercise.ExcerciseMainScreen
import com.navdeep.burn_down.nutrition.NutritionMainScreen
import com.navdeep.burn_down.yoga.YogaMainScreen

class ListviewAdapter(var mContext: Context,var itemHeight: Int, var dataSet: Array<Int>) :
        RecyclerView.Adapter<ListviewAdapter.ViewHolder>() {

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder)
     */
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageview: ImageView

        init {
            // Define click listener for the ViewHolder's View
            imageview = view.findViewById(R.id.imageview)
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.list_item_view_layout, viewGroup, false)

        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.imageview.layoutParams.height = itemHeight
        viewHolder.imageview.setBackgroundResource(dataSet.get(position))

        viewHolder.imageview.setOnClickListener {
            movetoNextScreen(position)
        }
    }

    private fun movetoNextScreen(position: Int) {
        when (position) {
            0 ->  mContext.startActivity(Intent(mContext , ExcerciseMainScreen :: class.java))
            1 ->  mContext.startActivity(Intent(mContext , YogaMainScreen :: class.java))
            2 ->  mContext.startActivity(Intent(mContext , NutritionMainScreen :: class.java))
            3 ->  mContext.startActivity(Intent(mContext , CalculateBmiScreen :: class.java))

        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size

}
