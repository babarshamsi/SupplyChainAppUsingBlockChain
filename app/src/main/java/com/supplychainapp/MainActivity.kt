package com.supplychainapp

import Model.Response.Chain
import Network.ApiInterface
import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.android.supplychainapp.R
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setUpListeners()

    val CAMERA_PERMISSION_REQUEST_CODE = 2
    val result = ContextCompat.checkSelfPermission(
        this.getApplicationContext(),
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    )
    if (result != PackageManager.PERMISSION_GRANTED) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(
                this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
        ) {
            Toast.makeText(
                this.getApplicationContext(),
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
//            val qrCode = QRCodeGenerator()
//            qrCode.codeGenerator("\n" +
//                    "Lorem Ipsum, sometimes referred to as 'lipsum', is the placeholder text used in design when creating content. It helps designers plan out where the content will sit, without needing to wait for the content to be written and approved. It originally comes from a Latin text, but to today's reader, it's seen as gibberish")
//            startActivity(Intent(this@
//            MainActivity, ScanViewActivity::class.java))

            val apiInterface = ApiInterface.create().getChain()

            //apiInterface.enqueue( Callback<List<Movie>>())
            apiInterface.enqueue( object : Callback<Chain> {
                override fun onResponse(call: Call<Chain>?, response: Response<Chain>?) {

                    if(response?.body() != null)
                        Toast.makeText(this@MainActivity, response.body().toString(), Toast.LENGTH_LONG).show()
//                        recyclerAdapter.setMovieListItems(response.body()!!)
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

        supplierButton?.setOnClickListener {
            startActivity(Intent(this@MainActivity, SupplierActivity::class.java))
        }
    }


}