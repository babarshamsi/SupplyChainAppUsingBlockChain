package com.supplychainapp

import Extensions.MANUFACTURER
import Extensions.SUPPLIER
import Extensions.unWrap
import Model.Response.SupplyResponse
import Model.Retailer
import Model.Supplier
import Model.WholeSeller
import Network.ApiInterface
import Utils.QRCodeGenerator
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.supplychainapp.R
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_q_r_code.*
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class QRCodeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_q_r_code)
        setUpListeners()

        val intent = intent
        val productInfo = intent.getStringExtra("BitmapImageData") as String
        checkProductInfoThenHitApi(productInfo)
        Toast.makeText(this, "Your Info has been Saved!", Toast.LENGTH_LONG).show()

    }

    private fun getSupplier(productInfo: String?): Supplier {
        try {
            val obj = JSONObject(productInfo)
            val gson = Gson()
            return gson.fromJson(obj.toString(), Supplier::class.java)
        } catch (t: Throwable) {
            Log.e("My App", "Could not parse malformed JSON: \"" + "\"")
        }
        return Supplier()
    }

    private fun getManufacturer(productInfo: String?): WholeSeller {
        try {
            val obj = JSONObject(productInfo)
            val gson = Gson()
            return gson.fromJson(obj.toString(), WholeSeller::class.java)
        } catch (t: Throwable) {
            Log.e("My App", "Could not parse malformed JSON: \"" + "\"")
        }
        return WholeSeller()
    }

    private fun getRetailer(productInfo: String?): Retailer {
        try {
            val obj = JSONObject(productInfo)
            val gson = Gson()
            return gson.fromJson(obj.toString(), Retailer::class.java)
        } catch (t: Throwable) {
            Log.e("My App", "Could not parse malformed JSON: \"" + "\"")
        }
        return Retailer()
    }

    private fun saveSupplierInfoAndGetBitmap(supplierInfo: String) {
        val qrCode = QRCodeGenerator()
        qrCodeImage?.setImageBitmap(qrCode.codeGenerator(supplierInfo))
        // when flow gets completed we will do mining
       val apiInterface = ApiInterface.create().doMining()

        apiInterface.enqueue(object : Callback<Void> {
            override fun onResponse(
                call: Call<Void>?,
                response: Response<Void>?
            ) {

                Log.d("MyApp", response.toString())
            }

            override fun onFailure(call: Call<Void>?, t: Throwable?) {
                Toast.makeText(this@QRCodeActivity, t.toString(), Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun convertToJsonStringWithIndex(supplierInfo: String, index: Int) =
        supplierInfo.replace("}", ",\"index\"" + ":" + "\"$index\"" + "}")

    private fun checkProductInfoThenHitApi(productInfo: String) {
        checkTypeForApi(productInfo)
    }

    private fun hitNewTransaction(productInfo: String, type: String) {
        when {
            type.equals(SUPPLIER) -> {
                hitSupplierApi(productInfo)
            }
            type.equals(MANUFACTURER) -> {
                hitManufacturerApi(productInfo)
            }
            else -> {
                hitRetailerApi(productInfo)
            }
        }
    }

    private fun hitSupplierApi(productInfo: String) {
        val apiInterface = ApiInterface.create().postSupplierInfo(getSupplier(productInfo))

        apiInterface.enqueue(object : Callback<SupplyResponse> {
            override fun onResponse(
                call: Call<SupplyResponse>?,
                response: Response<SupplyResponse>?
            ) {
                saveSupplierInfoAndGetBitmap(
                    convertToJsonStringWithIndex(
                        productInfo,
                        response?.body()?.index.unWrap()
                    )
                )

                Log.d("MyApp", response.toString())
            }

            override fun onFailure(call: Call<SupplyResponse>?, t: Throwable?) {
                Toast.makeText(this@QRCodeActivity, t.toString(), Toast.LENGTH_LONG).show()
            }
        })
    }
    private fun hitManufacturerApi(productInfo: String) {
        val apiInterface = ApiInterface.create().postWholeSellerInfo(getManufacturer(productInfo))

        apiInterface.enqueue(object : Callback<SupplyResponse> {
            override fun onResponse(
                call: Call<SupplyResponse>?,
                response: Response<SupplyResponse>?
            ) {
                saveSupplierInfoAndGetBitmap(
                    convertToJsonStringWithIndex(
                        productInfo,
                        response?.body()?.index.unWrap()
                    )
                )

                Log.d("MyApp", response.toString())
            }

            override fun onFailure(call: Call<SupplyResponse>?, t: Throwable?) {
                Toast.makeText(this@QRCodeActivity, t.toString(), Toast.LENGTH_LONG).show()
            }
        })
    }
    private fun hitRetailerApi(productInfo: String) {
        val apiInterface = ApiInterface.create().postRetailerInfo(getRetailer(productInfo))

        apiInterface.enqueue(object : Callback<SupplyResponse> {
            override fun onResponse(
                call: Call<SupplyResponse>?,
                response: Response<SupplyResponse>?
            ) {
                saveSupplierInfoAndGetBitmap(
                    convertToJsonStringWithIndex(
                        productInfo,
                        response?.body()?.index.unWrap()
                    )
                )

                Log.d("MyApp", response.toString())
            }

            override fun onFailure(call: Call<SupplyResponse>?, t: Throwable?) {
                Toast.makeText(this@QRCodeActivity, t.toString(), Toast.LENGTH_LONG).show()
            }
        })
    }



    private fun checkTypeForApi(productInfo: String) {
        val obj = JSONObject(productInfo)
        val type = obj.getString("type")
        hitNewTransaction(productInfo, type)

        }

    private fun startMainActivity() {
        val intent = Intent(applicationContext, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }

    private fun setUpListeners() {
        finishButton?.setOnClickListener {
            startMainActivity()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        startMainActivity()
    }

}