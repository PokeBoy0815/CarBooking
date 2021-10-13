package com.example.carbooking

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_details.*
import kotlinx.android.synthetic.main.carlist_item.*
import org.json.JSONObject
import java.util.*

class DetailsActivity : AppCompatActivity() {

    private var carID: Int = 0
    private val DEFAULT_TIME = "9:00"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        carID = intent.getIntExtra(IntentKeys.DETAIL_EXTRA.key, 0)
        getdetailJSON()
        initUI()

    }

    private fun initUI() {
        setTimeListener()
        setDateListener()
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DATE, 1)
        editTextTime.setText(DEFAULT_TIME)
        editTextTime.setRawInputType(InputType.TYPE_NULL)
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val month = calendar.get(Calendar.MONTH)
        val year = calendar.get(Calendar.YEAR)
        editTextDate.setText("$day.$month.$year")
        editTextDate.setRawInputType(InputType.TYPE_NULL)
        button.setOnClickListener{
            startBooking()
        }
    }

    private fun startBooking() {
        intent = Intent(this, BookedActivity::class.java)

        intent.putExtra(IntentKeys.BOOKING_ID.key, carID)
        val parseInt = Integer.parseInt(editTextDayCount.text.toString())
        intent.putExtra(IntentKeys.BOOKING_DURATION.key, parseInt)
        intent.putExtra(IntentKeys.BOOKING_NAME.key, name_tv.text.toString())
        intent.putExtra(IntentKeys.BOOKING_START_DATE.key, editTextDate.text.toString())
        intent.putExtra(IntentKeys.BOOKING_START_TIME.key, editTextTime.text.toString())

        startActivity(intent)
    }

    private fun setDateListener() {

        val calendar = Calendar.getInstance()
        val dialog = DatePickerDialog(
            this,
            getDatePickerListener(),
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        editTextDate.setOnClickListener {
            dialog.show()
        }
    }

    private fun getDatePickerListener() =
        DatePickerDialog.OnDateSetListener() { _, year, month, day ->
            // After selecting time - set text in TextView
            editTextDate.setText("$day.$month.$year")
        }

    private fun setTimeListener() {

        val calendar = Calendar.getInstance()
        val dialog = TimePickerDialog(
            this,
            getTimePickerListener(),
            calendar.get(Calendar.HOUR_OF_DAY),
            calendar.get(Calendar.MINUTE),
            true
        )

        editTextTime.setOnClickListener {
            dialog.show()
        }
    }

    private fun getTimePickerListener() =
        TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->

            val newMinute: String = "0$minute"

            if (minute < 10) editTextTime.setText("$hourOfDay:$newMinute")

            else editTextTime.setText("$hourOfDay:$minute")
        }

    private fun setViewProperties(jsonObject: JSONObject){
        getAndSetpicture(jsonObject.getString("image"))
        name_tv.text = jsonObject.getString("name")
        description_tv.text = jsonObject.getString("description")


    }

    private fun getAndSetpicture(imageName: String) {
        Picasso.get().load(
            //"http://job-applicants-dummy-api.kupferwerk.net.s3.amazonaws.com/api/images/$imageName" -> didn´t work -> Placeholder
            "http://job-applicants-dummy-api.kupferwerk.net.s3.amazonaws.com/api/images/automotive.jpg"
        ).into(imageView)
    }

    private fun getdetailJSON() {
        val queue = Volley.newRequestQueue(this)
        val url = "http://job-applicants-dummy-api.kupferwerk.net.s3.amazonaws.com/api/cars/$carID.json"

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            Response.Listener<JSONObject> { response ->
                setViewProperties(response)
            },
            Response.ErrorListener { Toast.makeText(this, "error in API", Toast.LENGTH_SHORT).show()})

        queue.add(jsonObjectRequest)
    }

}