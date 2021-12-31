package com.supplychainapp

import Extensions.unWrap
import Model.Response.SupplyResponse
import Model.Supplier
import Network.ApiInterface
import Utils.QRCodeGenerator
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.supplychainapp.R
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_q_r_code.*
import kotlinx.android.synthetic.main.activity_supplier.*
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class QRCodeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_q_r_code)

        val intent = intent
        val supplierInfo = intent.getStringExtra("BitmapImageData") as String
        hitApiToSaveDataToChain(supplierInfo)
        Toast.makeText(this, "Your Info has been Saved!", Toast.LENGTH_LONG).show()

    }

    private fun getSupplier(supplierInfo: String?): Supplier {
        try {
            val obj = JSONObject(supplierInfo)
            val gson = Gson()
            return gson.fromJson(obj.toString(), Supplier::class.java)
        } catch (t: Throwable) {
            Log.e("My App", "Could not parse malformed JSON: \"" + "\"")
        }
//        val supplier = Supplier(
//            supplierName = supplierName.text.toString().trim(),
//            retailerName = retailerName.text.toString().trim(),
//            pickUpDate = pickUpDate.text.toString().trim(),
//            deliverDate = deliverDate?.text.toString().trim(),
//            pickUpFrom = pickUpFrom.text.toString().trim(),
//            deliverTo = deliverTo.text.toString().trim()
//        )
        return Supplier()
    }

    private fun saveSupplierInfoAndGetBitmap(supplierInfo: String) {
        val qrCode = QRCodeGenerator()
        qrCodeImage?.setImageBitmap(qrCode.codeGenerator(supplierInfo))
    }

    private fun convertToJsonStringWithIndex(supplierInfo: String, index: Int) =
        supplierInfo.replace("}", ",\"index\"" + ":" + "\"$index\"" + "}")

    private fun hitApiToSaveDataToChain(supplierInfo: String) {
        val apiInterface = ApiInterface.create().postSupplierInfo(
            supplier = getSupplier(
                supplierInfo
            )
        )

        apiInterface.enqueue(object : Callback<SupplyResponse> {
            override fun onResponse(
                call: Call<SupplyResponse>?,
                response: Response<SupplyResponse>?
            ) {
                saveSupplierInfoAndGetBitmap(convertToJsonStringWithIndex(supplierInfo, response?.body()?.index.unWrap()))

                Log.d("MyApp", response.toString())
            }

            override fun onFailure(call: Call<SupplyResponse>?, t: Throwable?) {
                Toast.makeText(this@QRCodeActivity, t.toString(), Toast.LENGTH_LONG).show()
            }
        })
    }

}