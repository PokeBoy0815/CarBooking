package com.example.carbooking

import android.content.Context
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson
import java.util.UUID

class DataHandler(private val context: Context) {

    private val gson = Gson()
    private val prefs = context.getSharedPreferences("app_storage", Context.MODE_PRIVATE)

    private val db = FirebaseFirestore.getInstance()

    private fun loadJsonFromAssets(fileName: String): String {
        return context.assets.open(fileName).bufferedReader().use { it.readText() }
    }

    fun loadAppData(): AppData {
        val json = loadJsonFromAssets("carData.json")
        return gson.fromJson(json, AppData::class.java)
    }

    fun getCars(
        onSuccess: (List<Car>) -> Unit,
        onError: (Exception) -> Unit
    ) {
        db.collection("cars")
            .get()
            .addOnSuccessListener { snapshot ->
                val cars = snapshot.documents.mapNotNull { doc ->
                    val brand = doc.getString("brand") ?: return@mapNotNull null
                    val model = doc.getString("model") ?: return@mapNotNull null

                    val pricePerDay = doc.getDouble("pricePerDay") ?: 0.0
                    val location = doc.getString("location") ?: ""
                    val available = doc.getBoolean("available") ?: true
                    val image = doc.getString("image") ?: "placeholder"
                    val seats = (doc.getLong("seats") ?: 0L).toInt()
                    val transmission = doc.getString("transmission") ?: ""
                    val fuel = doc.getString("fuel") ?: ""
                    val description = doc.getString("description") ?: ""

                    Car(
                        id = doc.id,
                        brand = brand,
                        model = model,
                        pricePerDay = pricePerDay,
                        location = location,
                        available = available,
                        image = image,
                        seats = seats,
                        transmission = transmission,
                        fuel = fuel,
                        description = description
                    )
                }

                onSuccess(cars)
            }
            .addOnFailureListener { e ->
                onError(e)
            }
    }

    fun getCarById(id: String): Car? = loadAppData().cars.find { it.id == id }

    fun getBookings(
        onSuccess: (List<Booking>) -> Unit,
        onError: (Exception) -> Unit
    ) {
        db.collection("bookings")
            .orderBy("createdAt") // optional
            .get()
            .addOnSuccessListener { snapshot ->
                val bookings = snapshot.documents.mapNotNull { doc ->
                    val carId = doc.getString("carId") ?: return@mapNotNull null
                    val customerName = doc.getString("customerName") ?: ""
                    val startDate = doc.getString("startDate") ?: ""
                    val endDate = doc.getString("endDate") ?: ""

                    Booking(
                        id = doc.id,
                        carId = carId,
                        customerName = customerName,
                        startDate = startDate,
                        endDate = endDate
                    )
                }
                onSuccess(bookings)
            }
            .addOnFailureListener { e ->
                onError(e)
            }
    }

    fun addBooking(
        carId: String,
        customerName: String,
        startDate: String,
        endDate: String,
        onSuccess: (String) -> Unit,
        onError: (Exception) -> Unit
    ) {
        val data = hashMapOf(
            "carId" to carId,
            "customerName" to customerName,
            "startDate" to startDate,
            "endDate" to endDate,
            "createdAt" to FieldValue.serverTimestamp()
        )

        db.collection("bookings")
            .add(data) // Firestore generiert Document-ID
            .addOnSuccessListener { docRef ->
                onSuccess(docRef.id)
            }
            .addOnFailureListener { e ->
                onError(e)
            }
    }

    fun deleteBooking(
        bookingId: String,
        onSuccess: () -> Unit,
        onError: (Exception) -> Unit
    ) {
        db.collection("bookings")
            .document(bookingId)
            .delete()
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { e -> onError(e) }
    }


    //fun getBookingByName(name: String): Booking? = loadAppData().bookings.find { it.customerName == name }




}