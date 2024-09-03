package com.example.tanatornandroid

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CarDetailsActivity : AppCompatActivity() {

    private lateinit var listView: ListView
    private lateinit var btnAddNewCar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_car_details)

        listView = findViewById(R.id.listView)
        btnAddNewCar = findViewById(R.id.btn_add_new_car)

        loadCarDetails()

        btnAddNewCar.setOnClickListener {
            val intent = Intent(this, AddCarActivity::class.java)
            startActivity(intent)
        }
    }

    private fun loadCarDetails() {
        val apiService = RetrofitClient.getClient().create(ApiService::class.java)
        val call = apiService.getAllCars()

        call.enqueue(object : Callback<List<Car>> {
            override fun onResponse(call: Call<List<Car>>, response: Response<List<Car>>) {
                if (response.isSuccessful) {
                    val carList = response.body()
                    if (carList != null) {
                        val adapter = CarListAdapter(this@CarDetailsActivity, carList)
                        listView.adapter = adapter
                    }
                } else {
                    // แสดงข้อความแจ้งเตือนเมื่อดึงข้อมูลไม่สำเร็จ
                }
            }

            override fun onFailure(call: Call<List<Car>>, t: Throwable) {
                // แสดงข้อความแจ้งเตือนเมื่อเกิดข้อผิดพลาดในการดึงข้อมูล
            }
        })
    }
}
