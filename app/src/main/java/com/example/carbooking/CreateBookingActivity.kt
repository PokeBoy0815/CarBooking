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

        val carId = intent.getStringExtra("carId") ?: run {
            Toast.makeText(this, "Fehler: carId fehlt.", Toast.LENGTH_LONG).show()
            finish()
            return
        }

        val etName = findViewById<EditText>(R.id.etName)
        val etStart = findViewById<EditText>(R.id.etStartDate)
        val etEnd = findViewById<EditText>(R.id.etEndDate)
        val btnConfirm = findViewById<Button>(R.id.btnFinishBooking)

        val handler = DataHandler(this)

        var isSaving = false

        btnConfirm.setOnClickListener {
            if (isSaving) return@setOnClickListener

            val name = etName.text.toString().trim()
            val start = etStart.text.toString().trim()
            val end = etEnd.text.toString().trim()

            if (name.isEmpty() || start.isEmpty() || end.isEmpty()) {
                Toast.makeText(this, "Bitte alle Felder ausfüllen.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            isSaving = true
            btnConfirm.isEnabled = false

            handler.addBooking(
                carId = carId,
                customerName = name,
                startDate = start,
                endDate = end,
                onSuccess = { bookingId ->
                    Toast.makeText(this, "Buchung gespeichert! ($bookingId)", Toast.LENGTH_SHORT).show()
                    finish()
                },
                onError = { e ->
                    isSaving = false
                    btnConfirm.isEnabled = true
                    Toast.makeText(this, "Fehler beim Speichern: ${e.message}", Toast.LENGTH_LONG).show()
                }
            )
        }
    }
}