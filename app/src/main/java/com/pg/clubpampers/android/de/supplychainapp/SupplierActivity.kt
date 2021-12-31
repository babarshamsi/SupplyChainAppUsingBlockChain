package com.pg.clubpampers.android.de.supplychainapp

import Extensions.unWrap
import Model.Response.SupplyResponse
import Model.Supplier
import Network.ApiInterface
import Utils.QRCodeGenerator
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_supplier.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*


class SupplierActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_supplier)
        setupListeners()
        setUpPickUpDate()
        setUpDeliverDate()
    }

    private fun setUpPickUpDate() {
        val datePickerForPickUp =
            MaterialDatePicker.Builder.datePicker()
        datePickerForPickUp.setTitleText("Select date")
            .build()
        val pickerForPickUp = datePickerForPickUp.build()
        pickUpDate?.setOnClickListener {
            pickerForPickUp.show(supportFragmentManager, "tag")
        }
        pickerForPickUp.addOnPositiveButtonClickListener {
            val timeZoneUTC: TimeZone = TimeZone.getDefault()
            val offsetFromUTC: Int = timeZoneUTC.getOffset(Date().time) * -1
            val simpleFormat = SimpleDateFormat("dd, MMM yyyy", Locale.US)
            val date = Date(it + offsetFromUTC)
            pickUpDate?.setText(simpleFormat.format(date))

        }
    }

    private fun setUpDeliverDate() {
        val datePickerForDeliver =
            MaterialDatePicker.Builder.datePicker()
        datePickerForDeliver.setTitleText("Select date")
            .build()
        val pickerForDeliver = datePickerForDeliver.build()
        deliverDate?.setOnClickListener {
            pickerForDeliver.show(supportFragmentManager, "tag")
        }

        pickerForDeliver.addOnPositiveButtonClickListener {
            val timeZoneUTC: TimeZone = TimeZone.getDefault()
            val offsetFromUTC: Int = timeZoneUTC.getOffset(Date().time) * -1
            val simpleFormat = SimpleDateFormat("dd, MMM yyyy", Locale.US)
            val date = Date(it + offsetFromUTC)
            deliverDate?.setText(simpleFormat.format(date))
        }
    }

    private fun setupListeners() {
        saveSupplierButton?.setOnClickListener {
            saveSupplierInfoToBlockChain()
        }
    }

    private fun getSupplierInfo(index: Int): String {
        val supplier = Supplier(
            supplierName = supplierName.text.toString().trim(),
            retailerName = retailerName.text.toString().trim(),
            pickUpDate = pickUpDate.text.toString().trim(),
            deliverDate = deliverDate?.text.toString().trim(),
            pickUpFrom = pickUpFrom.text.toString().trim(),
            deliverTo = deliverTo.text.toString().trim()
        )
        val supplierInfo = Gson().toJson(supplier)
        return convertToJsonString(supplierInfo, index)
    }

    private fun convertToJsonString(supplierInfo: String, index: Int) =
        supplierInfo.replace("}", ",\"index\""+":"+"\"$index\""+"}")

    private fun getSupplier(): Supplier {
        val supplier = Supplier(
            supplierName = supplierName.text.toString().trim(),
            retailerName = retailerName.text.toString().trim(),
            pickUpDate = pickUpDate.text.toString().trim(),
            deliverDate = deliverDate?.text.toString().trim(),
            pickUpFrom = pickUpFrom.text.toString().trim(),
            deliverTo = deliverTo.text.toString().trim()
        )
        return supplier
    }

    private fun saveSupplierInfoAndGetBitmap(index: Int): Bitmap? {
        val qrCode = QRCodeGenerator()
        return qrCode.codeGenerator(getSupplierInfo(index))
    }

    private fun saveSupplierInfoToBlockChain() {
        val apiInterface = ApiInterface.create().postSupplierInfo(supplier = getSupplier())

        apiInterface.enqueue( object : Callback<SupplyResponse> {
            override fun onResponse(call: Call<SupplyResponse>?, response: Response<SupplyResponse>?) {
                Log.d("MyApp", response.toString())
                openQRCodeActivity(response?.body()?.index.unWrap())
            }

            override fun onFailure(call: Call<SupplyResponse>?, t: Throwable?) {
                Toast.makeText(this@SupplierActivity, t.toString(), Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun openQRCodeActivity(index: Int){
        val intent = Intent(this, QRCodeActivity::class.java)
        intent.putExtra("BitmapImage", saveSupplierInfoAndGetBitmap(index))
        startActivity(intent)
    }
}


