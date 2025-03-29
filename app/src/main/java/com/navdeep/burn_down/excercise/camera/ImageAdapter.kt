package com.navdeep.burn_down.excercise.camera

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter
import com.navdeep.burn_down.R

class ImageAdapter(
    private val imageList: Array<Int> // List of drawable resource IDs
) : PagerAdapter() {

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view = LayoutInflater.from(container.context)
            .inflate(R.layout.filter_item_layout, container, false)

        val imageView = view.findViewById<ImageView>(R.id.imageView)
        imageView.setImageResource(imageList[position])

        container.addView(view)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun getCount(): Int {
        return imageList.size
    }
}

//class ImageViewPagerAdapter(
//    private val imageList: Array<Int> // List of image URLs or resource IDs
//) : RecyclerView.Adapter<ImageViewPagerAdapter.ImageViewHolder>() {
//
//    inner class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        val imageView: ImageView = itemView.findViewById(R.id.imageView)
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
//        val view = LayoutInflater.from(parent.context)
//            .inflate(R.layout.item_image_layout, parent, false)
//        return ImageViewHolder(view)
//    }
//
//    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
//        val imageUrl = imageList[position]
//
//        holder.imageView?.setBackgroundResource(imageUrl)
//
//    }
//
//    override fun getItemCount(): Int = imageList.size
//}



//
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.ImageView
//import androidx.recyclerview.widget.RecyclerView
//import com.navdeep.burn_down.R
//
//class ImageAdapter(private val imageList: Array<Int>) : RecyclerView.Adapter<ImageAdapter.ImageViewHolder>() {
//
//    // ViewHolder class to hold the ImageView
//    class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        val imageView: ImageView = itemView.findViewById(R.id.imageView)
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
//        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_image_layout, parent, false)
//        return ImageViewHolder(view)
//    }
//
//    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
//        val imageUrl = imageList[position]
//
//        holder.imageView?.setBackgroundResource(imageUrl)
//
//    }
//
//    override fun getItemCount(): Int {
//        return imageList.size
//    }
//}
