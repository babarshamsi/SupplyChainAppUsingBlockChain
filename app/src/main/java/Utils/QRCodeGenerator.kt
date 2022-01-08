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

    fun codeGenerator(inputValue: String): Bitmap? {

        val point = Point()
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
        } catch (e: Exception) {
            Log.v("TAG", e.toString());
        }
        return qrgEncoder.bitmap
    }

}