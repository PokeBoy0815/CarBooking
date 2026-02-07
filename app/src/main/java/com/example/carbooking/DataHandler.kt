package com.example.carbooking

import android.content.Context
import com.google.gson.Gson

// später ersetzen mit Firebase data
class DataHandler(private val context: Context) {

    private fun loadJsonFromAssets(fileName: String): String {
        return context.assets.open(fileName).bufferedReader().use { it.readText() }
    }

    fun loadAppData(): AppData {
        val json = loadJsonFromAssets("carData.json")
        return Gson().fromJson(json, AppData::class.java)
    }

    fun getCars(): List<Car> = loadAppData().cars

    fun getCarById(id: String): Car? = loadAppData().cars.find { it.id == id }

    fun getBookings(): List<Booking> = loadAppData().bookings

    fun getBookingByNamer(name: String): Booking? = loadAppData().bookings.find { it.customerName == name }

    fun safeBooking(booking: Booking) {
        val bookings = loadAppData().bookings.toMutableList()
        bookings.add(booking)
    }



}