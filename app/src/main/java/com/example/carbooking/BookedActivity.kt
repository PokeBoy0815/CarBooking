package com.example.carbooking

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.carbooking.ListAssets.BookingItem
import com.example.carbooking.ListAssets.BookingItemAdapter
import kotlinx.android.synthetic.main.activity_main.*

class BookedActivity: AppCompatActivity() {

    private lateinit var bookingItemAdapter: BookingItemAdapter
    private val bookingItemArray: MutableList<BookingItem> = mutableListOf()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_booked)
        initializeAdapter()

        val id = intent.getIntExtra(IntentKeys.BOOKING_ID.key, 0)
        val duration = intent.getIntExtra(IntentKeys.BOOKING_DURATION.key, 0)
        val name = intent.getStringExtra(IntentKeys.BOOKING_NAME.key)
        val date = intent.getStringExtra(IntentKeys.BOOKING_START_DATE.key)
        val time = intent.getStringExtra(IntentKeys.BOOKING_START_TIME.key)
        populateArray(id, duration, name, date, time)
        rvMainList.layoutManager = LinearLayoutManager(this)
    }

    private fun populateArray(id: Int, duration: Int, name: String?, date: String?, time: String?) {
        val item = BookingItem(
            id,
            name,
            date,
            time,
            duration
        )
        bookingItemArray.add(item)
        bookingItemAdapter.notifyDataSetChanged()
    }

    private fun initializeAdapter() {
        bookingItemAdapter =
            BookingItemAdapter(bookingItemArray)
        rvMainList.adapter = bookingItemAdapter
    }
}