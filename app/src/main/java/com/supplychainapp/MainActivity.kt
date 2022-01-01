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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setUpListeners()
        supportActionBar?.setTitle("Home")

//        toolbar?.setTitle(getToolBarTitle())


        val CAMERA_PERMISSION_REQUEST_CODE = 2
        val result = ContextCompat.checkSelfPermission(
        this.applicationContext,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    )
    if (result != PackageManager.PERMISSION_GRANTED) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(
                this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
        ) {
            Toast.makeText(
                this.applicationContext,
                "External Storage permission needed. Please allow in App Settings for additional functionality.",
                Toast.LENGTH_LONG
            ).show()
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                CAMERA_PERMISSION_REQUEST_CODE
            )
        }
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

     fun getToolBarTitle() = "Home"

    private fun setUpListeners() {
        consumerButton?.setOnClickListener {
            startActivity(Intent(this@MainActivity, ScanViewActivity::class.java))
        }

        manufacturerButton?.setOnClickListener {
            startActivity(Intent(this@MainActivity, ManufacturerActivity::class.java))
        }

        supplierButton?.setOnClickListener {
            startActivity(Intent(this@MainActivity, SupplierActivity::class.java))
        }

        retailerButton?.setOnClickListener {
            startActivity(Intent(this@MainActivity, RetailerActivity::class.java))
        }
    }


}