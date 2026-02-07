package com.example.carbooking

data class AppData (
    val cars: List<Car>,
    val bookings: List<Booking>
)

data class Car(
    val id: String,
    val brand: String,
    val model: String,
    val pricePerDay: Double,
    val location: String,
    val available: Boolean,
    val image: String,
    val seats: Int,
    val transmission: String,
    val fuel: String,
    val description: String
)

data class Booking(
    val id: String,
    val carId: String,
    val customerName: String,
    val startDate: String,
    val endDate: String
)