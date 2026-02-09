package com.example.carbooking

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class CarAdapter(
    private val context: Context,
    private val cars: List<Car>,
    private val onCarClick: (Car) -> Unit
) : RecyclerView.Adapter<CarAdapter.CarViewHolder>() {

    inner class CarViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivCar: ImageView = itemView.findViewById(R.id.ivCar)
        val tvTitle: TextView = itemView.findViewById(R.id.tvTitle)
        val tvPrice: TextView = itemView.findViewById(R.id.tvPrice)
        val tvLocation: TextView = itemView.findViewById(R.id.tvLocation)
        val tvAvailability: TextView = itemView.findViewById(R.id.tvAvailability)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.car_item, parent, false)
        return CarViewHolder(view)
    }

    override fun onBindViewHolder(holder: CarViewHolder, position: Int) {
        val car = cars[position]
        holder.tvTitle.text = "${car.brand} ${car.model}"
        holder.tvPrice.text = "${car.pricePerDay} € / Tag"
        holder.tvLocation.text = car.location
        holder.tvAvailability.text = if (car.available) "Verfügbar" else "Nicht verfügbar"

        // Bild aus drawable (später DB) über Namen (später Key) laden
        val resId = context.resources.getIdentifier(car.image, "drawable", context.packageName)
        if (resId != 0) holder.ivCar.setImageResource(resId)

        holder.itemView.setOnClickListener { onCarClick(car) }
    }

    // auslesbarmachen der Anzahl an items
    override fun getItemCount(): Int = cars.size
}