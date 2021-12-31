package com.supplychainapp

import Extensions.visibility
import Model.Supplier
import Utils.GenericDialogFragment
import Utils.SingletonForProduct.isManufacturerInfoAdded
import Utils.SingletonForProduct.isSupplierInfoAdded
import Utils.isAddMoreInfoConditionValid
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.android.supplychainapp.R
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
        addMoreInfoVisibility()
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

    private fun getSupplierInfo(): String {
        val supplier = Supplier(
            supplierName = supplierName.text.toString().trim(),
            retailerName = retailerName.text.toString().trim(),
            pickUpDate = pickUpDate.text.toString().trim(),
            deliverDate = deliverDate?.text.toString().trim(),
            pickUpFrom = pickUpFrom.text.toString().trim(),
            deliverTo = deliverTo.text.toString().trim()
        )
        return Gson().toJson(supplier)
    }





    private fun saveSupplierInfoToBlockChain() {
        openQRCodeActivityORAddMoreInfo()
    }

    private fun openQRCodeActivityORAddMoreInfo() {
        if (isAddMoreInfoConditionValid) {
            saveProductInfoAndProceed()
        } else {
            showAlertPopUp()
        }

    }

    private fun showAlertPopUp() {
        GenericDialogFragment.showAddMoreInfoErrorDialog(this,
            { Yes ->
                if (isManufacturerInfoAdded) {
                    openRetailer()
                } else {
                    openManufacturer()
                }
            }) { No ->
            saveProductInfoAndProceed()
        }
    }

    private fun openRetailer() {
        val intent = Intent(this, RetailerActivity::class.java)
        startActivity(intent)
    }

    private fun openManufacturer() {
        val intent = Intent(this, ManufacturerActivity::class.java)
        startActivity(intent)
    }

    private fun saveProductInfoAndProceed() {
        val intent = Intent(this, QRCodeActivity::class.java)
        intent.putExtra("BitmapImageData", getSupplierInfo())
        isSupplierInfoAdded = true
        startActivity(intent)
    }

    private fun addMoreInfoVisibility() = addMoreInfo?.visibility(isAddMoreInfoConditionValid)
}


