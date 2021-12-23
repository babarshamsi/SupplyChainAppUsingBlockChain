package Utils

import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.Point
import android.os.Environment
import android.util.Log
import androidmads.library.qrgenearator.QRGContents
import androidmads.library.qrgenearator.QRGEncoder
import androidmads.library.qrgenearator.QRGSaver


class QRCodeGenerator {

    private val savePath: String =
        Environment.getExternalStorageDirectory().getPath().toString() + "/QRCode/"
    private var bitmap: Bitmap? = null
    private val qrgEncoder: QRGEncoder? = null

    fun codeGenerator(inputValue: String) {

//        val manager = getSystemService as WindowManager?
//        val display = manager!!.defaultDisplay
        val point = Point()
//        display.getSize(point)
        val width: Int = point.x
        val height: Int = point.y
        var smallerDimension = if (width < height) width else height
        smallerDimension = smallerDimension * 3 / 4

        // Initializing the QR Encoder with your value to be encoded, type you required and Dimension
        val qrgEncoder = QRGEncoder(inputValue, null, QRGContents.Type.TEXT, smallerDimension);
        qrgEncoder.colorBlack = Color.BLACK;
        qrgEncoder.colorWhite = Color.WHITE;
        try {
            // Getting QR-Code as Bitmap
            val qrgSaver = QRGSaver()
            qrgSaver.save(
                savePath,
                "myImage",
                qrgEncoder.bitmap,
                QRGContents.ImageType.IMAGE_JPEG
            )
            saveQRCode()
            // Setting Bitmap to ImageView
//        qrImage.setImageBitmap(bitmap);
        } catch (e: Exception) {
            Log.v("TAG", e.toString());
        }
    }

    private fun saveQRCode() {
        // Save with location, value, bitmap returned and type of Image(JPG/PNG).
        // Save with location, value, bitmap returned and type of Image(JPG/PNG).

    }
}