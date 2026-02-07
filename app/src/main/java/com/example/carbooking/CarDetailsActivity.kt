package com.example.carbooking

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class CarDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_car_detail)

        val carID = intent.getStringExtra("carId")
        if (carID == null) {
            //error message
            finish()
            return
        }

        val car = DataLoader(this).getCarById(carID)
        if (car == null) {
            //error message
            finish()
            return
        }

        val iv = findViewById<ImageView>(R.id.ivDetailCar)
        val tvTitle = findViewById<TextView>(R.id.tvDetailTitle)
        val tvMeta = findViewById<TextView>(R.id.tvDetailMeta)
        val tvDescription = findViewById<TextView>(R.id.tvDetailDescription)

        tvTitle.text = "${car?.brand} ${car?.model}"
        tvMeta.text = "${car?.location}, ${car?.seats} Sitze, ${car?.transmission}, ${car?.fuel}, ${car?.pricePerDay} € / Tag"
        tvDescription.text = car?.description

        val resId = resources.getIdentifier(car?.image, "drawable", packageName)
        if (resId != 0) iv.setImageResource(resId)
        }


}