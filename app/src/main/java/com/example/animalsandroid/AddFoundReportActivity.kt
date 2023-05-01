package com.example.animalsandroid

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.animalsandroid.databinding.ActivityAddFoundReportBinding
import java.io.ByteArrayOutputStream
import java.text.SimpleDateFormat
import java.util.*

class AddFoundReportActivity : AppCompatActivity() {

    private lateinit var pickedBitMap: Bitmap
    private lateinit var bilding: ActivityAddFoundReportBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bilding = ActivityAddFoundReportBinding.inflate(layoutInflater)
        setContentView(bilding.root)
        //setContentView(R.layout.activity_add_found_report)
    }

    private fun toastMsg(msg:String){
        val toast = Toast.makeText(this,msg, Toast.LENGTH_LONG)
        toast.show()
    }

    fun displayToastMsg(v: View){
        getData()
        if(pickedBitMap == null) {
            toastMsg("Nie podano wszystkich danych")
        }
        else {
            toastMsg("Zatwierdzono")
            sendData()
        }
    }

    private fun getData() {
    }

    private fun sendData(){
        val intent = Intent()

        setResult(1, intent)
        finish()
    }

    private fun compressBitmap(bitmap: Bitmap): ByteArray {
        val outputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, outputStream)
        return outputStream.toByteArray()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(galleryIntent, 2)
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    fun pickedPhoto (view: View){
        if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE), 1)
        }else{
            val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(galleryIntent, 2)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode == 2 && resultCode == Activity.RESULT_OK && data != null ){
            var pickedPhoto: Uri = data.data!!
            pickedBitMap = if (Build.VERSION.SDK_INT >= 28){
                ImageDecoder.decodeBitmap(ImageDecoder.createSource(this.contentResolver, pickedPhoto!!))
            }else{
                MediaStore.Images.Media.getBitmap(this.contentResolver, pickedPhoto)
            }
            bilding.imageView.setImageBitmap(pickedBitMap)
            bilding.textViewAddPhoto.text = ""
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    fun pickDate(view: View){
        var formatDate = SimpleDateFormat( "dd MMMM YYYY", Locale.ROOT)
        val getDate = Calendar.getInstance()

        val datePicker = DatePickerDialog(this, android.R.style.Theme_Holo_Light_Dialog_MinWidth,
            DatePickerDialog.OnDateSetListener { datePicker, i, i2, i3 ->
                val selectDate = Calendar.getInstance()
                selectDate.set(Calendar.YEAR, i)
                selectDate.set(Calendar.MONTH, i2)
                selectDate.set(Calendar.DAY_OF_MONTH, i3)
                val date = formatDate.format(selectDate.time)
                findViewById<EditText>(R.id.editTextDate).setText(date)
            }, getDate.get(Calendar.YEAR), getDate.get(Calendar.MONTH), getDate.get(Calendar.DAY_OF_MONTH))
        datePicker.show()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        try {
            outState?.putByteArray("IMAGE", compressBitmap(pickedBitMap))
        }catch(ignored : UninitializedPropertyAccessException){ }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        val savedByteArray = savedInstanceState?.getByteArray("IMAGE")
        if(savedByteArray != null && savedByteArray.isNotEmpty()){
            pickedBitMap = BitmapFactory.decodeByteArray(savedByteArray, 0, savedByteArray.size)
            bilding.imageView.setImageBitmap(pickedBitMap)
            bilding.textViewAddPhoto.text = ""}
    }
}