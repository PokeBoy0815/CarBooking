package com.example.carbooking.ListAssets

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.carbooking.R
import kotlinx.android.synthetic.main.booking_item.view.*

class BookingItemAdapter(
    private val bookingItems: MutableList<BookingItem>
) : RecyclerView.Adapter<BookingItemAdapter.BookingItemViewHolder>() {

    class BookingItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookingItemViewHolder {
        return BookingItemViewHolder(

            LayoutInflater.from(parent.context).inflate(
                R.layout.booking_item,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return bookingItems.size
    }

    override fun onBindViewHolder(holder: BookingItemViewHolder, position: Int) {
        val curBookingItem = bookingItems[position]
        holder.itemView.apply {
            tvCarNameBooking.text = curBookingItem.carName
            tvCarContext.text = "Date: " + curBookingItem.bookingDate + ", Time " + curBookingItem.bookingTime + ", Duration: " + curBookingItem.bookingDuration
        }
    }
}