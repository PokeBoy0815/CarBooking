package com.example.carbooking

import android.content.Context
import com.google.gson.Gson

// später ersetzen mit Firebase data
class DataLoader(private val context: Context) {

    private fun loadJsonFromAssets(fileName: String): String {
        return context.assets.open(fileName).bufferedReader().use { it.readText() }
    }

    fun loadAppData(): AppData {
        val json = loadJsonFromAssets("data.json")
        return Gson().fromJson(json, AppData::class.java)
    }

    fun getCars(): List<Car> = loadAppData().cars

}