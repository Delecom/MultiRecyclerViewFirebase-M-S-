package com.example.FirebaseWork

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecyclerViewAdapter(context: Context,var list: ArrayList<Data>,val multiTypeClicks: MultiTypeClicks) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val VIEW_TYPE_ONE = 1
        const val VIEW_TYPE_TWO = 2
    }

    private val context: Context = context


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == VIEW_TYPE_ONE) {
            return View1ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_view_1, parent, false)
            )
        }
        return View2ViewHolder(
            LayoutInflater.from(context).inflate(R.layout.item_view_2, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (list[position].viewType == VIEW_TYPE_ONE) {
            (holder as View1ViewHolder)
            holder.textViewHamza.text = "Hamzza"
            holder.itemView.setOnClickListener {
                multiTypeClicks.delete(position)
            }
            holder.textViewHamza.setOnClickListener {
                multiTypeClicks.edit()
            }
        } else {
            (holder as View2ViewHolder)
            holder.message.text = "Mahboob"
            holder.message.setOnClickListener {
                multiTypeClicks.openNewClass("MainActivity")
            }
            holder.imageView.setOnClickListener {
                multiTypeClicks.openNewClass("Delete$position")
            }

        }
    }

    override fun getItemViewType(position: Int): Int {
        return list[position].viewType
    }



    private inner class View1ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        var textViewHamza: TextView = itemView.findViewById(R.id.textViewHamza)

    }

    private inner class View2ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        var message: TextView = itemView.findViewById(R.id.textView)
        var imageView: ImageView = itemView.findViewById(R.id.imageView)

    }
}