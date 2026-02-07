package com.example.carbooking

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class CarListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_car_list)

        val rv = findViewById<RecyclerView>(R.id.rvCars)
        rv.layoutManager = LinearLayoutManager(this)

        val cars = DataLoader(this).getCars()

        rv.adapter = CarAdapter(this, cars) { car ->
            val intent = Intent(this, CarDetailsActivity::class.java)
            intent.putExtra("carId", car.id)
            startActivity(intent)
        }

        /*
        enableEdgeToEdge()
        setContent {
            CarBookingTheme {
                Scaffold( modifier = Modifier.fillMaxSize() ) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
        */
    }
}
