package com.example.FirebaseWork.hamza


import android.view.View
import android.view.ViewGroup
import com.smarteist.autoimageslider.SliderViewAdapter
import java.util.*

class SliderAdaptor() :
    SliderViewAdapter<SliderAdaptor.VH>() {
    private var mSliderItems = ArrayList<String>()
    fun renewItems(sliderItems: ArrayList<String>) {
        mSliderItems = sliderItems
        notifyDataSetChanged()
    }

    fun addItem(sliderItem: String) {
        mSliderItems.add(sliderItem)
        notifyDataSetChanged()
    }

  override fun onCreateViewHolder(parent: ViewGroup): VH {
//        val inflate: View = LayoutInflater.from(parent.context).inflate(R.layout.image_holder, null)
//        return VH(inflate)
       TODO("Not yet implemented")

  }

    override fun onBindViewHolder(viewHolder: VH, position: Int) {
        //load image into view
//        Picasso.get().load(mSliderItems[position]).fit().into(viewHolder.imageView)
    }


    override fun getCount(): Int {
        return mSliderItems.size
    }

    inner class VH(itemView: View) : ViewHolder(itemView) {
//        var imageView: ImageView = itemView.findViewById(R.id.imageSlider)

    }


}