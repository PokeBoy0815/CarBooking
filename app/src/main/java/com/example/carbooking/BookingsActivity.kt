package com.example.carbooking

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.carbooking.ui.theme.CarBookingTheme

class BookingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bookings)

        val rv = findViewById<RecyclerView>(R.id.rvBookings)
        rv.layoutManager = LinearLayoutManager(this)

        val loader = DataHandler(this)
        val bookings = loader.getBookings()

        // booking + car mergen (damit es schön angezeigt wird)
        val displayItems = bookings.map { booking ->
            val car = loader.getCarById(booking.carId)
            val carId = booking.carId
            val carTitle = if (car != null) {
                "${car.brand} ${car.model} • ${car.location}"
            } else {
                "Unbekanntes Auto (${booking.carId})"
            }

            BookingDisplayItem(
                bookingId = booking.id,
                carId = carId,
                carTitle = carTitle,
                customerName = booking.customerName,
                dateRange = "${booking.startDate} bis ${booking.endDate}"
            )
        }

        rv.adapter = BookingAdapter(displayItems)
    }
}
