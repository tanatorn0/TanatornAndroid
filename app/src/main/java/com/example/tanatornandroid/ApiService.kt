package com.example.tanatornandroid

import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*

data class Car(
    val id: Int?,
    val brand_name: String,
    val model_name: String,
    val year_of_manufacture: Int,
    val color: String,
    val price: Double,
    val gear_type: String,
    val fuel_type: String,
    val number_of_doors: Int,
    val number_of_seats: Int,
    val image_url: String?
)

interface ApiService {

    // สำหรับการอัปโหลดรูปภาพพร้อมข้อมูลรถยนต์
    @Multipart
    @POST("/api/cars")
    fun addCarWithImage(
        @Part("brand_name") brand_name: String,
        @Part("model_name") model_name: String,
        @Part("year_of_manufacture") year_of_manufacture: Int,
        @Part("color") color: String,
        @Part("price") price: Double,
        @Part("gear_type") gear_type: String,
        @Part("fuel_type") fuel_type: String,
        @Part("number_of_doors") number_of_doors: Int,
        @Part("number_of_seats") number_of_seats: Int,
        @Part image: MultipartBody.Part
    ): Call<Void>

    // สำหรับการส่งข้อมูลรถยนต์เป็น JSON โดยไม่อัปโหลดรูปภาพ
    @POST("/api/cars")
    fun addCar(@Body car: Car): Call<Void>

    // ดึงรายละเอียดของรถยนต์โดยใช้ ID
    @GET("/api/cars/{id}")
    fun getCarDetails(@Path("id") id: Int): Call<Car>

    // ดึงข้อมูลรถยนต์ทั้งหมด
    @GET("/api/cars")
    fun getAllCars(): Call<List<Car>>
}
