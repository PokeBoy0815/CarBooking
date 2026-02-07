package com.example.carbooking

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class BookingAdapter(
    private val items: List<BookingDisplayItem>
) : RecyclerView.Adapter<BookingAdapter.BookingViewHolder>() {

    inner class BookingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvCarTitle: TextView = itemView.findViewById(R.id.tvBookingCarTitle)
        val tvCustomer: TextView = itemView.findViewById(R.id.tvBookingCustomer)
        val tvDateRange: TextView = itemView.findViewById(R.id.tvBookingDateRange)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookingViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.booking_item, parent, false)
        return BookingViewHolder(view)
    }

    override fun onBindViewHolder(holder: BookingViewHolder, position: Int) {
        val item = items[position]
        holder.tvCarTitle.text = item.carTitle
        holder.tvCustomer.text = "Name: ${item.customerName}"
        holder.tvDateRange.text = "Zeitraum: ${item.dateRange}"
    }

    override fun getItemCount(): Int = items.size
}
