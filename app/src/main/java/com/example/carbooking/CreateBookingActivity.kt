package com.example.carbooking

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class CreateBookingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_booking)

        val carId = intent.getStringExtra("carId")
        if (carId == null) {
            finish()
            return
        }

        val bookingButton = findViewById<Button>(R.id.btnFinishBooking)
        bookingButton.setOnClickListener {

        }



    }
}