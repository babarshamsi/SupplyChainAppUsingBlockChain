package com.pg.clubpampers.android.de.supplychainapp

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.SparseArray
import android.view.SurfaceHolder
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.vision.CameraSource
import com.google.android.gms.vision.Detector
import com.google.android.gms.vision.barcode.Barcode
import com.google.android.gms.vision.barcode.BarcodeDetector
import kotlinx.android.synthetic.main.activity_scanned_barcode.*
import java.io.IOException


class ScannedBarcodeActivity : AppCompatActivity() {


//    lateinit var surfaceView: SurfaceView
//    TextView txtBarcodeValue;
    lateinit var barcodeDetector: BarcodeDetector
    lateinit var cameraSource: CameraSource
    val REQUEST_CAMERA_PERMISSION = 201;
//    Button btnAction;
    var intentData = ""
    var isEmail = false

    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_scanned_barcode);
        initViews();
        super.onCreate(savedInstanceState)
    }

    private fun initViews() {
//        txtBarcodeValue = findViewById(R.id.txtBarcodeValue);
//        surfaceView = findViewById(R.id.surfaceView);
//        btnAction = findViewById(R.id.btnAction);

        btnAction.setOnClickListener {
            if (intentData.length > 0) {
                    if (isEmail)
                        startActivity(Intent(this, EmailActivity::class.java).putExtra("email_address", intentData));
                    else {
                        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(intentData)));
                    }
                }
        }



//        btnAction.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (intentData.length() > 0) {
//                    if (isEmail)
//                        startActivity(new Intent(ScannedBarcodeActivity.this, EmailActivity.class).putExtra("email_address", intentData));
//                    else {
//                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(intentData)));
//                    }
//                }
//            }
//        });
    }

    private fun initialiseDetectorsAndSources() {

        Toast.makeText(getApplicationContext(), "Barcode scanner started", Toast.LENGTH_SHORT).show();
        barcodeDetector = BarcodeDetector.Builder(this)
                .setBarcodeFormats(Barcode.ALL_FORMATS)
                .build();

        cameraSource = CameraSource.Builder(this, barcodeDetector)
        .setRequestedPreviewSize(1920, 1080)
                .setAutoFocusEnabled(true) //you should add this feature
                .build();

        surfaceView.holder.addCallback(object : SurfaceHolder.Callback {
            override fun surfaceCreated(holder: SurfaceHolder) {
                try {
                    if (ActivityCompat.checkSelfPermission(this@ScannedBarcodeActivity,
                                    Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                        cameraSource.start(surfaceView.holder);
                    } else {
                        ActivityCompat.requestPermissions(this@ScannedBarcodeActivity,
                                arrayOf(Manifest.permission.CAMERA), REQUEST_CAMERA_PERMISSION);
                    }

                } catch (e: IOException) {
                    e.printStackTrace();
                }
            }

            override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
//                TODO("Not yet implemented")
            }

            override fun surfaceDestroyed(holder: SurfaceHolder) {
                cameraSource.stop();
            }

        })

        barcodeDetector.setProcessor(object : Detector.Processor<Barcode> {
            override fun release() {
                Toast.makeText(getApplicationContext(), "To prevent memory leaks barcode scanner has been stopped", Toast.LENGTH_SHORT).show();
            }

            override fun receiveDetections(detections: Detector.Detections<Barcode>?) {
                val barcodes = detections?.detectedItems as SparseArray<Barcode>
                if (barcodes.size() != 0) {
                    txtBarcodeValue.post(object :Runnable {
                        override fun run() {
                            if (barcodes.valueAt(0).email != null) {
                                txtBarcodeValue.removeCallbacks(null);
                                intentData = barcodes.valueAt(0).email.address;
                                txtBarcodeValue.setText(intentData);
                                isEmail = true;
                                btnAction.setText("ADD CONTENT TO THE MAIL");
                            } else {
                                isEmail = false;
                                btnAction.setText("LAUNCH URL");
                                intentData = barcodes.valueAt(0).displayValue;
                                txtBarcodeValue.setText(intentData);
                            }
                        }
                    });
                }
            }

        })


        barcodeDetector.setProcessor(object : Detector.Processor<Barcode> {

            override fun release() {
//                TODO("Not yet implemented")
            }

            override fun receiveDetections(p0: Detector.Detections<Barcode>?) {
//                TODO("Not yet implemented")
            }
        });
    }

    override fun onPause() {
        super.onPause()
        cameraSource.release()
    }

    override fun onResume() {
        super.onResume()
        initialiseDetectorsAndSources()
    }
}