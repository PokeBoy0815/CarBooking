package com.example.carbooking

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.example.carbooking.ListAssets.CarItem
import com.example.carbooking.ListAssets.CarItemAdapter
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    private lateinit var carItemAdapter: CarItemAdapter
    private val carItemArray: MutableList<CarItem> = mutableListOf()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))
        initializeAdapter()
        getJsonFromURL()
        Toast.makeText(this, IntentKeys.DETAIL_EXTRA.key, Toast.LENGTH_SHORT).show()


    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun initializeAdapter(){
        carItemAdapter =
            CarItemAdapter(carItemArray)
        rvMainList.adapter = carItemAdapter
        carItemAdapter.setOnItemClickListener(object: CarItemAdapter.onItemClickListener{
            override fun onItemClick(position: Int) {
                startIntentForPosition(position)
            }

        })
        rvMainList.layoutManager = LinearLayoutManager(this)
        carItemAdapter.notifyDataSetChanged()
    }

    private fun startIntentForPosition(position: Int) {
        val intent = Intent(this, DetailsActivity::class.java)
        intent.putExtra(IntentKeys.DETAIL_EXTRA.key, carItemArray[position].id)
        startActivity(intent)
    }

    private fun putItemsInArray(jsonArray: JSONArray) {
        for (i: Int in 0 until jsonArray.length()){
            val jobj: JSONObject = jsonArray.getJSONObject(i)
            val id: Int = jobj.getInt("id")
            val name: String = jobj.getString("name")
            val sDes: String = try {jobj.getString("shortDescription")} catch (e: JSONException) { "No description available" }
            val car: CarItem =
                CarItem(id, name, sDes)
            carItemArray.add(car)
            carItemAdapter.notifyDataSetChanged()
        }


    }

    fun getJsonFromURL(){

        val queue = Volley.newRequestQueue(this)
        val url = "http://job-applicants-dummy-api.kupferwerk.net.s3.amazonaws.com/api/cars.json"

        val jsonArrayRequest = JsonArrayRequest(
            Request.Method.GET, url, null,
            Response.Listener<JSONArray> { response ->
                putItemsInArray(response)
            },
            Response.ErrorListener { Toast.makeText(this, "error in API", Toast.LENGTH_SHORT).show()})

        queue.add(jsonArrayRequest)


    }



}