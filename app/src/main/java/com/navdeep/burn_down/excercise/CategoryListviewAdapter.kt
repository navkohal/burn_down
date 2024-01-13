package com.navdeep.burn_down.excercise

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.navdeep.burn_down.R
import com.navdeep.burn_down.excercise.workout.WorkoutScreen

class CategoryListviewAdapter(var mContext: Context, var dataSet: Array<Int>, var titles: Array<String>) :
        RecyclerView.Adapter<CategoryListviewAdapter.ViewHolder>() {

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder)
     */
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var imageview: ImageView ?= null
        var excerciseTitle: TextView ?= null

        init {
            // Define click listener for the ViewHolder's View
            imageview = view.findViewById(R.id.imageview)
            excerciseTitle = view.findViewById(R.id.title_tv)
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.category_item_listview, viewGroup, false)

        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.imageview?.setBackgroundResource(dataSet.get(position))
        viewHolder.excerciseTitle?.setText(titles.get(position))

        viewHolder.imageview?.setOnClickListener {
            mContext.startActivity(Intent(mContext , WorkoutScreen :: class.java))
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size

}
