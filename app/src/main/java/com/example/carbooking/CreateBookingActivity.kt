package com.example.carbooking

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
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

        val dataHandler = DataHandler(this)

        val etName = findViewById<EditText>(R.id.etName)
        val etStart = findViewById<EditText>(R.id.etStartDate)
        val etEnd = findViewById<EditText>(R.id.etEndDate)

        val bookingButton = findViewById<Button>(R.id.btnFinishBooking)
        bookingButton.setOnClickListener {
            val name = etName.text.toString().trim()
            val start = etStart.text.toString().trim()
            val end = etEnd.text.toString().trim()

            // felder werden danach mit DatePicker implementiert jetzt nur basic check ob nicht leer
            if (name.isEmpty() || start.isEmpty() || end.isEmpty()) {
                Toast.makeText(this, "Bitte alle Felder ausfüllen.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val booking = Booking(
                id = dataHandler.newBookingId(),
                carId = carId,
                customerName = name,
                startDate = start,
                endDate = end
            )

            dataHandler.addBooking(booking)
            Toast.makeText(this, "Buchung gespeichert!", Toast.LENGTH_SHORT).show()

            //muss noch die Autos auf nicht verfügbar setzen wenn man auf alles zugreifen kann

            finish()

        }


    }
}