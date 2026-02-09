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

        // 1) carId aus Intent lesen
        val carId = intent.getStringExtra("carId") ?: run {
            Toast.makeText(this, "Fehler: carId fehlt.", Toast.LENGTH_LONG).show()
            finish()
            return
        }

        // 2) Views holen
        val etName = findViewById<EditText>(R.id.etName)
        val etStart = findViewById<EditText>(R.id.etStartDate)
        val etEnd = findViewById<EditText>(R.id.etEndDate)
        val btnConfirm = findViewById<Button>(R.id.btnFinishBooking)

        // 3) DataLoader Instanz
        val handler = DataHandler(this)

        // Optional: Button gegen Double-Click schützen
        var isSaving = false

        btnConfirm.setOnClickListener {
            if (isSaving) return@setOnClickListener

            val name = etName.text.toString().trim()
            val start = etStart.text.toString().trim()
            val end = etEnd.text.toString().trim()

            // 4) Minimal-Validierung
            if (name.isEmpty() || start.isEmpty() || end.isEmpty()) {
                Toast.makeText(this, "Bitte alle Felder ausfüllen.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // 5) Speichern starten (Firestore)
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