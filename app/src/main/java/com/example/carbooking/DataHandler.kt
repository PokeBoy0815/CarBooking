package com.example.carbooking

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.UUID

// später ersetzen mit Firebase data
class DataHandler(private val context: Context) {

    private val gson = Gson()
    private val prefs = context.getSharedPreferences("app_storage", Context.MODE_PRIVATE)

    private fun loadJsonFromAssets(fileName: String): String {
        return context.assets.open(fileName).bufferedReader().use { it.readText() }
    }

    fun loadAppData(): AppData {
        val json = loadJsonFromAssets("carData.json")
        return gson.fromJson(json, AppData::class.java)
    }

    fun getCars(): List<Car> = loadAppData().cars

    fun getCarById(id: String): Car? = loadAppData().cars.find { it.id == id }

    fun getBookings(): List<Booking> {
        val json = prefs.getString("bookings", null) ?: return emptyList()
        val type = object : TypeToken<List<Booking>>() {}.type
        return gson.fromJson(json, type) ?: emptyList()
    }

    fun newBookingId(): String = "book_" + UUID.randomUUID().toString()

    fun addBooking(booking: Booking) {
        val current = getBookings().toMutableList()
        current.add(booking)
        prefs.edit().putString("bookings", gson.toJson(current)).apply()
    }

    fun deleteBooking(bookingId: String) {
        val current = getBookings().toMutableList()
        val newList = current.filterNot { it.id == bookingId }
        prefs.edit().putString("bookings", gson.toJson(newList)).apply()
    }


    //fun getBookingByName(name: String): Booking? = loadAppData().bookings.find { it.customerName == name }




}