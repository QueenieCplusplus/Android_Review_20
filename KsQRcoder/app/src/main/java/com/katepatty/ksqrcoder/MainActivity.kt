package com.katepatty.ksqrcoder

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.WriterException
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    var vector: Drawable? = null
    var usersText: String = " "


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var qr = generateQRCode()
        vector = drawableConverter(qr)
        //qrcode.setImageBitmap()
        qrcode.setImageDrawable(vector)

        btn.setOnClickListener {
            trigger()
        }

    }

    private fun trigger(){

        usersText= input?.text.toString()
        val qr = textToQR(usersText)
        // 觸動畫面到第二頁面讓新生成的二維碼輸出
        // 或作吐出提醒

        alertQRcode(qr)

    }

    private fun alertQRcode(bitmap: Bitmap){

        //產生Builder物件
        val builder = AlertDialog.Builder(this)
        builder.setMessage("NewQRcode process is busy, try next time.")
                .setPositiveButton("OK", null)
                .setNeutralButton("Cancel", null)
                .show()

        //intentNewView(bitmap)

    }

    private fun intentNewView(bitmap: Bitmap){

        //val intent = Intent(this, NewActivity::class.java)
        intent.putExtra("NewQRCode", bitmap)
        startActivity(intent)

        // 另外一畫面接收方式
        //val intent = getIntent()
        //val draw = intent.getParcelableExtra<Parcelable>("NewQRCode") as Drawable?

    }


    private fun drawableConverter(bitmap: Bitmap): Drawable {

        val d: Drawable = BitmapDrawable(resources, bitmap)
        return d

    }

    private fun generateQRCode(): Bitmap {
        val text = "https://www.facebook.com/pages/category/App-Page/PattyAppier-277134069609204/"
        val width = 500
        val height = 500
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val codeWriter = MultiFormatWriter()

        try {
            val bitMatrix = codeWriter.encode(text, BarcodeFormat.QR_CODE, width, height)
            for (x in 0 until width) {
                for (y in 0 until height) {
                    bitmap.setPixel(x, y, if (bitMatrix[x, y]) Color.BLACK else Color.WHITE)
                }
            }
        } catch (e: WriterException) {
            Log.d("gg la", "generateQRCode: ${e.message}")
        }
        return bitmap
    }

    private fun textToQR(text: String): Bitmap{
            val text = "I luv Patty & Kate"
            val width = 16
            val height = 16
            val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
            val codeWriter = MultiFormatWriter()

            try {
                val bitMatrix = codeWriter.encode(text, BarcodeFormat.QR_CODE, width, height)
                for (x in 0 until width) {
                    for (y in 0 until height) {
                        bitmap.setPixel(x, y, if (bitMatrix[x, y]) Color.BLACK else Color.WHITE)
                    }
                }
            } catch (e: WriterException) {
                Log.d("gg la", "generateQRCode: ${e.message}")
            }
            return bitmap

    }

}