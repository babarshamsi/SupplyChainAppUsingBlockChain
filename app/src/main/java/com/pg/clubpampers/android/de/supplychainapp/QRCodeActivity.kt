package com.pg.clubpampers.android.de.supplychainapp

import android.graphics.Bitmap
import android.os.Bundle
import android.os.Parcelable
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_q_r_code.*


class QRCodeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_q_r_code)

        val intent = intent
        val bitmap = intent.getParcelableExtra<Parcelable>("BitmapImage") as Bitmap?
        qrCode?.setImageBitmap(bitmap)
        Toast.makeText(this, "Your Info has been Saved!",Toast.LENGTH_LONG).show()

    }
}