package com.example.tanatornandroid

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso

class CarListAdapter(private val context: Context, private val carList: List<Car>) : BaseAdapter() {

    override fun getCount(): Int = carList.size

    override fun getItem(position: Int): Any = carList[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View = convertView ?: LayoutInflater.from(context).inflate(R.layout.item_car, parent, false)

        val car = carList[position]

        // Find all views in item_car.xml
        val brandNameTextView = view.findViewById<TextView>(R.id.tv_brand_name)
        val modelNameTextView = view.findViewById<TextView>(R.id.tv_model_name)
        val yearOfManufactureTextView = view.findViewById<TextView>(R.id.tv_year_of_manufacture)
        val colorTextView = view.findViewById<TextView>(R.id.tv_color)
        val priceTextView = view.findViewById<TextView>(R.id.tv_price)
        val gearTypeTextView = view.findViewById<TextView>(R.id.tv_gear_type)
        val fuelTypeTextView = view.findViewById<TextView>(R.id.tv_fuel_type)
        val numberOfDoorsTextView = view.findViewById<TextView>(R.id.tv_number_of_doors)
        val numberOfSeatsTextView = view.findViewById<TextView>(R.id.tv_number_of_seats)
        val carImageView = view.findViewById<ImageView>(R.id.iv_car_image)

        // Set data to views
        brandNameTextView.text = car.brand_name
        modelNameTextView.text = car.model_name
        yearOfManufactureTextView.text = car.year_of_manufacture.toString()
        colorTextView.text = car.color
        priceTextView.text = car.price.toString()
        gearTypeTextView.text = car.gear_type
        fuelTypeTextView.text = car.fuel_type
        numberOfDoorsTextView.text = car.number_of_doors.toString()
        numberOfSeatsTextView.text = car.number_of_seats.toString()

        // Load image using Picasso
        Picasso.get().load(car.image_url).into(carImageView)

        return view
    }
}
