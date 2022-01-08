package com.supplychainapp

import Extensions.WHOLESELER
import Extensions.capsFirstLetter
import Model.Response.SupplyResponse
import Model.WholeSeller
import Network.ApiInterface
import Utils.AlertDialogFragment
import Utils.SingletonForProduct
import Utils.SingletonForProduct.isAllInfoHasBeenSaved
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

class WholeSellerActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wholeseller)
        supportActionBar?.title = WHOLESELER.capsFirstLetter()
        setupListeners()
        setUpPickUpDate()
        setUpDeliverDate()
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
        finishButton?.setOnClickListener {
            showAlertPopUp()
        }

        continueButton?.setOnClickListener {
            SingletonForProduct.isWholeSellerInfoAdded = true
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
        val apiInterface = ApiInterface.create().postWholeSellerInfo(getWholeSeller())

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

    private fun getWholeSeller(): WholeSeller {
        val wholeSeller = WholeSeller(
            senderName = senderName?.text.toString().trim(),
            receiverName = receiverName?.text.toString().trim(),
            pickUpDate = pickUpDate?.text.toString().trim(),
            deliverDate = deliverDate?.text.toString().trim(),
            pickUpFrom = pickUpFrom?.text.toString().trim(),
            deliverTo = deliverTo?.text.toString().trim()
        )
        return wholeSeller
    }


    private fun getWholeSellerInfo(): String {
        val supplier = WholeSeller(
            senderName = senderName.text.toString().trim(),
            receiverName = receiverName.text.toString().trim(),
            pickUpDate = pickUpDate.text.toString().trim(),
            deliverDate = deliverDate?.text.toString().trim(),
            pickUpFrom = pickUpFrom.text.toString().trim(),
            deliverTo = deliverTo.text.toString().trim()
        )
        return Gson().toJson(supplier)
    }

    private fun showAlertPopUp() {
        AlertDialogFragment.showAddMoreInfoErrorDialog(this, WholeSeller().type,
            { Yes ->
                saveProductInfoAndProceed()
            }) { No ->
//            dismiss
        }
    }

    private fun openDesiredScreen() {
        if (getProductInfoDecider.screenToOpen() != null) {
            val intent = Intent(this, getProductInfoDecider.screenToOpen())
            startActivity(intent)
        }
    }

    private fun saveProductInfoAndProceed() {
        val intent = Intent(this, QRCodeActivity::class.java)
        intent.putExtra("BitmapImageData", getWholeSellerInfo())
        startActivity(intent)
    }

}


