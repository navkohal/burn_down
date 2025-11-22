package com.navdeep.burn_down.excercise.workout.excercises


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.bumptech.glide.Glide
import com.navdeep.burn_down.R
import com.navdeep.burn_down.model.BaseResponse



class ChooseExcerciseAdapter(
    var context: Context,
    var dataSet: ArrayList<BaseResponse>?,
    var onSelect: OnSelect
) : BaseAdapter() {

    interface OnSelect {
        fun onItemSelect(position : Int)
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        var myView = convertView
        var holder: ViewHolder

        if (myView == null) {
            myView = LayoutInflater.from(context).inflate(R.layout.select_excercise_item_layout, parent, false);
            holder = ViewHolder()
            holder.mTextView = myView!!.findViewById<TextView>(R.id.title_tv) as TextView
            holder.cardview = myView!!.findViewById<CardView>(R.id.cardview) as CardView
            holder.chk_imageview = myView!!.findViewById<ImageView>(R.id.check_iv) as ImageView
            holder.background_iv = myView!!.findViewById<ImageView>(R.id.background_iv) as ImageView
            myView.setTag(holder)
        } else {
            holder = myView.getTag() as ViewHolder

        }
        val res = context.resources.getIdentifier(
            dataSet?.get(position)?.gifUrl,
            "drawable",
            this.context.packageName
        )

        //play gif
        holder.background_iv?.let { imageView ->
            Glide.with(context)
                .load(res)
                .into(imageView)
        }

        holder.mTextView?.setText(dataSet?.get(position)?.name)
        if (dataSet?.get(position)?.isSelected!!) {
            holder.chk_imageview!!.visibility = View.VISIBLE
        } else {
            holder.chk_imageview!!.visibility = View.INVISIBLE
        }
        holder.cardview?.setOnClickListener {
            //todo chaneg boolean status
            onSelect?.onItemSelect(position)
        }

        return myView

    }

    override fun getItem(p0: Int): Any {
        return dataSet!!.get(p0)
    }

    override fun getItemId(p0: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return dataSet!!.size
    }

    class ViewHolder {
        var mTextView: TextView? = null
        var cardview: CardView? = null
        var chk_imageview: ImageView? = null
        var background_iv: ImageView? = null
    }

}


