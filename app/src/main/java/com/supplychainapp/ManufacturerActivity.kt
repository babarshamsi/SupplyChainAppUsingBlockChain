package com.supplychainapp

import Extensions.visibility
import Model.Manufacturer
import Model.Response.SupplyResponse
import Network.ApiInterface
import Utils.GenericDialogFragment
import Utils.SingletonForProduct
import Utils.SingletonForProduct.isAllInfoHasBeenSaved
import Utils.isAddMoreInfoConditionValid
import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.android.supplychainapp.R
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_supplier.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

class ManufacturerActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manufacturer)
        setupListeners()
        setUpPickUpDate()
        setUpDeliverDate()
//        addMoreInfoVisibility()
    }

     fun getToolBarTitle() = "Manufacturer"


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
        saveInfoButton?.setOnClickListener {
            saveSupplierInfoToBlockChain()
        }
        addMoreInfo?.setOnClickListener {
            openDesiredScreen()
        }
        finishButton?.setOnClickListener {
            showAlertPopUp()
        }

        continueButton?.setOnClickListener {
            SingletonForProduct.isManufacturerInfoAdded = true
            if (isAllInfoHasBeenSaved) {
                saveProductInfoAndProceed()
            }
            else {
                hitNewTransaction()
                openDesiredScreen()
            }
        }
    }

    private fun hitNewTransaction() {
        val apiInterface = ApiInterface.create().postManufacturerInfo(getManufacturer())

        apiInterface.enqueue(object : Callback<SupplyResponse> {
            override fun onResponse(
                call: Call<SupplyResponse>?,
                response: Response<SupplyResponse>?
            ) {

                Log.d("MyApp", response.toString())
            }

            override fun onFailure(call: Call<SupplyResponse>?, t: Throwable?) {
                Log.d("MyApp", t.toString())
            }
        })
    }

    private fun getManufacturer(): Manufacturer {
        val manufacturer = Manufacturer(
            senderName = senderName?.text.toString().trim(),
            receiverName = receiverName?.text.toString().trim(),
            pickUpDate = pickUpDate?.text.toString().trim(),
            deliverDate = deliverDate?.text.toString().trim(),
            pickUpFrom = pickUpFrom?.text.toString().trim(),
            deliverTo = deliverTo?.text.toString().trim()
        )
        return manufacturer
    }


    private fun getManufacturerInfo(): String {
        val supplier = Manufacturer(
            senderName = senderName.text.toString().trim(),
            receiverName = receiverName.text.toString().trim(),
            pickUpDate = pickUpDate.text.toString().trim(),
            deliverDate = deliverDate?.text.toString().trim(),
            pickUpFrom = pickUpFrom.text.toString().trim(),
            deliverTo = deliverTo.text.toString().trim()
        )
        return Gson().toJson(supplier)
    }


    private fun saveSupplierInfoToBlockChain() {
        SingletonForProduct.isManufacturerInfoAdded = true
        openQRCodeActivityORAddMoreInfo()
    }

    private fun openQRCodeActivityORAddMoreInfo() {
        if (isAllInfoHasBeenSaved) {
            saveProductInfoAndProceed()
        } else {
            showAlertPopUp()
        }

    }

    private fun showAlertPopUp() {
        GenericDialogFragment.showAddMoreInfoErrorDialog(this, Manufacturer().type,
            { Yes ->
                saveProductInfoAndProceed()
//                if (SingletonForProduct.isRetailerInfoAdded) {
//                    openRetailer()
//                } else {
//                    openManufacturer()
//                }
            }) { No ->
//            dismiss
//            saveProductInfoAndProceed()
        }
    }

    private fun openDesiredScreen() {
        if (getProductInfoDecider.screenToOpen() != null) {
            val intent = Intent(this, getProductInfoDecider.screenToOpen())
            startActivity(intent)
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
        intent.putExtra("BitmapImageData", getManufacturerInfo())
        startActivity(intent)
    }

    private fun addMoreInfoVisibility() = addMoreInfo?.visibility(isAddMoreInfoConditionValid)
}


