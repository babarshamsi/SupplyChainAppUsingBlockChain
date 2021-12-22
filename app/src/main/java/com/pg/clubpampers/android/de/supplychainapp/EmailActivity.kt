package com.pg.clubpampers.android.de.supplychainapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_email.*
import kotlinx.android.synthetic.main.activity_main.*


class EmailActivity: AppCompatActivity(), View.OnClickListener {
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btnScanBarcode -> startActivity(
                Intent(
                    this@EmailActivity,
                    ScannedBarcodeActivity::class.java
                )
            )
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_email)
        initViews()
    }

    private fun initViews() {
        if (intent.getStringExtra("email_address") != null) {
            txtEmailAddress.setText("Recipient : " + intent.getStringExtra("email_address"))
        }
        btnSendEmail.setOnClickListener(View.OnClickListener {
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "text/plain"
            intent.putExtra(
                Intent.EXTRA_EMAIL,
                arrayOf<String>(txtEmailAddress.getText().toString())
            )
            intent.putExtra(Intent.EXTRA_SUBJECT, inSubject.getText().toString().trim())
            intent.putExtra(Intent.EXTRA_TEXT, inBody.getText().toString().trim())
            startActivity(Intent.createChooser(intent, "Send Email"))
        })
    }
}