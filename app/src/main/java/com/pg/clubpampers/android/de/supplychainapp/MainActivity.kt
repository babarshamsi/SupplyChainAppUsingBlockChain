package com.pg.clubpampers.android.de.supplychainapp

import Utils.QRCodeGenerator
import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
//    var btnScanBarcode: Button? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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
            val qrCode = QRCodeGenerator()
            qrCode.codeGenerator("\n" +
                    "Lorem Ipsum, sometimes referred to as 'lipsum', is the placeholder text used in design when creating content. It helps designers plan out where the content will sit, without needing to wait for the content to be written and approved. It originally comes from a Latin text, but to today's reader, it's seen as gibberish")
//            startActivity(Intent(this@MainActivity, ScannedBarcodeActivity::class.java))
        }
    }


}