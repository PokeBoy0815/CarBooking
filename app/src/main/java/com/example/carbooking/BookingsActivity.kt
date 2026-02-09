package com.example.carbooking

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class BookingsActivity : AppCompatActivity() {

    private lateinit var rv: RecyclerView
    private lateinit var handler: DataHandler

    // Cache: carId -> Car
    private var carMap: Map<String, Car> = emptyMap()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bookings)

        rv = findViewById<RecyclerView>(R.id.rvBookings)
        rv.layoutManager = LinearLayoutManager(this)

        handler = DataHandler(this)


        loadCarsThenBookings()
    }

    private fun loadCarsThenBookings() {
        handler.getCars(
            onSuccess = { cars ->
                carMap = cars.associateBy { it.id }
                loadAndShowBookings() // jetzt haben wir die Cars im Cache
            },
            onError = { e ->
                Toast.makeText(this, "Fehler beim Laden der Autos: ${e.message}", Toast.LENGTH_LONG).show()
            }
        )
    }
    private fun loadAndShowBookings() {
        handler.getBookings(
            onSuccess = { bookings ->
                val displayItems = bookings.map { booking ->
                    val car = carMap[booking.carId]

                    val carTitle = if (car != null) {
                        "${car.brand} ${car.model} • ${car.location}"
                    } else {
                        "Unbekanntes Auto (${booking.carId})"
                    }

                    BookingDisplayItem(
                        bookingId = booking.id,
                        carTitle = carTitle,
                        customerName = booking.customerName,
                        dateRange = "${booking.startDate} bis ${booking.endDate}",
                        carId = booking.carId
                    )
                }

                rv.adapter = BookingAdapter(displayItems) { item ->
                    // Löschen auch async über Firestore
                    handler.deleteBooking(
                        bookingId = item.bookingId,
                        onSuccess = {
                            Toast.makeText(this, "Buchung storniert", Toast.LENGTH_SHORT).show()
                            loadAndShowBookings() // nur Bookings neu laden, Cars bleiben gecached
                        },
                        onError = { e ->
                            Toast.makeText(this, "Fehler beim Löschen: ${e.message}", Toast.LENGTH_LONG).show()
                        }
                    )
                }
            },
            onError = { e ->
                Toast.makeText(this, "Fehler beim Laden der Buchungen: ${e.message}", Toast.LENGTH_LONG).show()
            }
        )
    }

}
