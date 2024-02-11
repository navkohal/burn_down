package com.navdeep.burn_down.excercise.favorite

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.navdeep.burn_down.R
import com.navdeep.burn_down.db.FavoriteDataClass

class FavoriteWorkoutAdapter(var mContext: Context, var fav_list: ArrayList<FavoriteDataClass>) :
    RecyclerView.Adapter<FavoriteWorkoutAdapter.ViewHolder>() {

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder)
     */
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var imageview: ImageView?= null
        var excerciseTitle: TextView?= null
        var date_title: TextView?= null

        init {
            // Define click listener for the ViewHolder's View
            imageview = view.findViewById(R.id.imageview)
            excerciseTitle = view.findViewById(R.id.title_tv)
            date_title = view.findViewById(R.id.date_tv)
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.fav_item_listview, viewGroup, false)

        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.imageview?.setBackgroundResource(getBgImage(fav_list.get(position).yourModelList.get(0).bodyPart))
        viewHolder.excerciseTitle?.setText(fav_list.get(position).yourModelList.get(0).bodyPart)
        viewHolder.date_title?.setText(fav_list.get(position).completedDate)

//        viewHolder.imageview?.setOnClickListener {
//            moveToNextScreen(position)
//        }
    }

    private fun getBgImage(bodyPart: String?) : Int {
        var image = R.drawable.cardio
        when (bodyPart) {
            "cardio" -> image =  R.drawable.cardio
            "upper legs" -> image =  R.drawable.legs_workout
            "lower legs" -> image =   R.drawable.legs_workout
            "chest" -> image =   R.drawable.chest_workout
            "back" -> image =  R.drawable.back_workout
            "shoulders" -> image =  R.drawable.shoulders_workout
            "waist" -> image =  R.drawable.abs_bg
            "arms" -> image =  R.drawable.arms_workout
        }

        return image
    }


    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = fav_list.size

}
