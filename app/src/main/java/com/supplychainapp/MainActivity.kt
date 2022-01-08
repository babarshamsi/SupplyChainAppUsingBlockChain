package com.supplychainapp

import Model.Response.Chain
import Network.ApiInterface
import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.android.supplychainapp.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toolbar_view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : BaseActivity() {

    val STORAGE_PERMISSION_REQUEST_CODE = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setUpListeners()
        supportActionBar?.title = "Home"

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
            == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                STORAGE_PERMISSION_REQUEST_CODE
            )
        }
        btnScanBarcode?.setOnClickListener {

            val apiInterface = ApiInterface.create().getChain()

            apiInterface.enqueue( object : Callback<Chain> {
                override fun onResponse(call: Call<Chain>?, response: Response<Chain>?) {

                    if(response?.body() != null)
                        Toast.makeText(this@MainActivity, response.body().toString(), Toast.LENGTH_LONG).show()
                }

                override fun onFailure(call: Call<Chain>?, t: Throwable?) {
                    Toast.makeText(this@MainActivity, t.toString(), Toast.LENGTH_LONG).show()

                }
            })
        }
    }

    private fun setUpListeners() {
        consumerButton?.setOnClickListener {
            startActivity(Intent(this@MainActivity, ScanViewActivity::class.java))
        }

        wholeSellerButton?.setOnClickListener {
            startActivity(Intent(this@MainActivity, WholeSellerActivity::class.java))
        }

        supplierButton?.setOnClickListener {
            startActivity(Intent(this@MainActivity, SupplierActivity::class.java))
        }

        retailerButton?.setOnClickListener {
            startActivity(Intent(this@MainActivity, RetailerActivity::class.java))
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == STORAGE_PERMISSION_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Storage permission granted", Toast.LENGTH_LONG).show()
            } else {
                finish()
                Toast.makeText(this, "Storage permission denied", Toast.LENGTH_LONG).show()
            }
        }
    }


}