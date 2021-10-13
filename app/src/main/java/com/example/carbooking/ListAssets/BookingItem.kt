package com.example.carbooking.ListAssets

data class BookingItem(
    val id: Int,
    val carName: String?,
    val bookingDate: String?,
    val bookingTime: String?,
    val bookingDuration: Int
) {
}