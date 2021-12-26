package com.pg.clubpampers.android.de.supplychainapp

import Model.Supplier
import Utils.QRCodeGenerator
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_supplier.*
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
            getSupplierInfo()
            saveSupplierInfo()
        }
    }

    private fun getSupplierInfo(): String {
        val supplier = Supplier(supplierName = supplierName.text.toString().trim(),
        retailerName = retailerName.text.toString().trim(), pickUpDate = pickUpDate.text.toString().trim(),
        deliverDate = deliverDate?.text.toString().trim(), pickUpFrom = pickUpFrom.text.toString().trim(),
        deliverTo = deliverTo.text.toString().trim())
        val supplierInfo = Gson().toJson(supplier)
        return supplierInfo
    }

    private fun saveSupplierInfo() {
                    val qrCode = QRCodeGenerator()
            qrCode.codeGenerator(getSupplierInfo())
//            startActivity(
//                Intent(this@
//            MainActivity, ScanViewActivity::class.java)
//            )
    }


}


