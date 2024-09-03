package com.example.tanatornandroid

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class AddCarActivity : AppCompatActivity() {

    private lateinit var brandName: EditText
    private lateinit var modelName: EditText
    private lateinit var yearOfManufacture: EditText
    private lateinit var color: EditText
    private lateinit var price: EditText
    private lateinit var gearType: EditText
    private lateinit var fuelType: EditText
    private lateinit var numberOfDoors: EditText
    private lateinit var numberOfSeats: EditText
    private lateinit var carImage: ImageView
    private lateinit var btnSelectImage: Button
    private lateinit var btnSave: Button

    private var selectedImageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_car)

        brandName = findViewById(R.id.et_brand_name)
        modelName = findViewById(R.id.et_model_name)
        yearOfManufacture = findViewById(R.id.et_year_of_manufacture)
        color = findViewById(R.id.et_color)
        price = findViewById(R.id.et_price)
        gearType = findViewById(R.id.et_gear_type)
        fuelType = findViewById(R.id.et_fuel_type)
        numberOfDoors = findViewById(R.id.et_number_of_doors)
        numberOfSeats = findViewById(R.id.et_number_of_seats)
        carImage = findViewById(R.id.iv_car_image)
        btnSelectImage = findViewById(R.id.btn_select_image)
        btnSave = findViewById(R.id.btn_save)

        btnSelectImage.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent, 100)
        }

        btnSave.setOnClickListener {
            val brandNameText = brandName.text.toString()
            val modelNameText = modelName.text.toString()
            val yearText = yearOfManufacture.text.toString()
            val colorText = color.text.toString()
            val priceText = price.text.toString()
            val gearTypeText = gearType.text.toString()
            val fuelTypeText = fuelType.text.toString()
            val numberOfDoorsText = numberOfDoors.text.toString()
            val numberOfSeatsText = numberOfSeats.text.toString()

            if (brandNameText.isEmpty() || modelNameText.isEmpty() || yearText.isEmpty() ||
                colorText.isEmpty() || priceText.isEmpty() || gearTypeText.isEmpty() ||
                fuelTypeText.isEmpty() || numberOfDoorsText.isEmpty() || numberOfSeatsText.isEmpty()) {
                Toast.makeText(this, "กรุณากรอกข้อมูลให้ครบถ้วน", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (selectedImageUri == null) {
                Toast.makeText(this, "กรุณาเลือกรูปภาพ", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val file = File(selectedImageUri!!.path)

            val car = Car(
                id = null,
                brand_name = brandNameText,
                model_name = modelNameText,
                year_of_manufacture = yearText.toInt(),
                color = colorText,
                price = priceText.toDouble(),
                gear_type = gearTypeText,
                fuel_type = fuelTypeText,
                number_of_doors = numberOfDoorsText.toInt(),
                number_of_seats = numberOfSeatsText.toInt(),
                image_url = file.path
            )

            val apiService = RetrofitClient.getClient().create(ApiService::class.java)
            val call = apiService.addCar(car)

            call.enqueue(object : Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    if (response.isSuccessful) {
                        Toast.makeText(this@AddCarActivity, "บันทึกข้อมูลสำเร็จ", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this@AddCarActivity, CarDetailsActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(this@AddCarActivity, "บันทึกข้อมูลล้มเหลว", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    Toast.makeText(this@AddCarActivity, "เกิดข้อผิดพลาด: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == 100) {
            selectedImageUri = data?.data
            carImage.setImageURI(selectedImageUri)
        }
    }
}
