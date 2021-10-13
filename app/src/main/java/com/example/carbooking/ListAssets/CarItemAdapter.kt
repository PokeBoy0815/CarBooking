
package com.example.carbooking.ListAssets

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.carbooking.R
import kotlinx.android.synthetic.main.carlist_item.view.*

class CarItemAdapter(
    private val carItems: MutableList<CarItem>
) : RecyclerView.Adapter<CarItemAdapter.CarItemViewHolder>() {

    class CarItemViewHolder(itemView: View, listener: onItemClickListener) : RecyclerView.ViewHolder(itemView){
        init {
            itemView.setOnClickListener{
                listener.onItemClick(adapterPosition)
            }
        }
    }

    private lateinit var mListner: onItemClickListener

    interface onItemClickListener {
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: onItemClickListener){

        mListner = listener

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarItemViewHolder {
        return CarItemViewHolder(

            LayoutInflater.from(parent.context).inflate(
                R.layout.carlist_item,
                parent,
                false
            ),
            mListner

        )
    }

    override fun getItemCount(): Int {
        return carItems.size
    }

    override fun onBindViewHolder(holder: CarItemViewHolder, position: Int) {
        val curCarItem = carItems[position]
        holder.itemView.apply {
            tvCarName.text = curCarItem.name
            tvCarShortDescription.text = curCarItem.shortDescription
        }
    }
}