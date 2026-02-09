package com.example.carbooking

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class CarListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_car_list)

        val rv = findViewById<RecyclerView>(R.id.rvCars)
        rv.layoutManager = LinearLayoutManager(this)

        val handler = DataHandler(this)

        handler.getCars(
            onSuccess = { cars ->
                rv.adapter = CarAdapter(this, cars) { car ->
                    val intent = Intent(this, CarDetailsActivity::class.java)
                    intent.putExtra("carId", car.id)
                    startActivity(intent)
                }
            },
            onError = { e ->
                Toast.makeText(this, "Fehler beim Laden der Autos: ${e.message}", Toast.LENGTH_LONG).show()
            }
        )

        val btnBookings = findViewById<Button>(R.id.btnBookings)
        btnBookings.setOnClickListener {
            val intent = Intent(this, BookingsActivity::class.java)
            startActivity(intent)
        }
    }

}

