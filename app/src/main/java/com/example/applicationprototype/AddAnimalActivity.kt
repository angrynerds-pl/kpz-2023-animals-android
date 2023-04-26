package com.example.applicationprototype

import android.app.Activity
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
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.applicationprototype.databinding.ActivityAddAnimalBinding
import java.io.ByteArrayOutputStream

class AddAnimalActivity : AppCompatActivity() {
    private lateinit var pickedBitMap: Bitmap
    private lateinit var bilding: ActivityAddAnimalBinding

    private lateinit var textName: String
    private lateinit var textLocality: String
    private lateinit var textDate: String
    private lateinit var concatText: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bilding = ActivityAddAnimalBinding.inflate(layoutInflater)
        setContentView(bilding.root)
        //setContentView(R.layout.activity_add_animal)
    }

    private fun toastMsg(msg:String){
        val toast = Toast.makeText(this,msg,Toast.LENGTH_LONG)
        toast.show()
    }

    fun displayToastMsg(v: View){
        val name = findViewById<EditText>(R.id.editTextName)
        val locality = findViewById<EditText>(R.id.editTextLocality)
        val date = findViewById<EditText>(R.id.editTextDate)

        if(name.text.toString().isEmpty() || locality.text.toString().isEmpty() ||
            date.text.toString().isEmpty() || pickedBitMap == null) {
            toastMsg("Nie podano wszystkich danych")
        }
        else {
            toastMsg("Zatwierdzono")
            getData()
            val intent = Intent()
            intent.putExtra("EXTRA_STRING", concatText)
            intent.putExtra("EXTRA_JPEG", compressBitmap(pickedBitMap))
            intent.putExtra("EXTRA_BOOLEAN", true)
            setResult(1, intent)
            //startActivity(intent)
            finish()
        }
    }

    private fun getData() {
        textName = findViewById<EditText>(R.id.editTextName).text.toString()
        textLocality = findViewById<EditText>(R.id.editTextLocality).text.toString()
        textDate = findViewById<EditText>(R.id.editTextDate).text.toString()
        concatText = textName.plus("\n").plus(textLocality).plus("\n").plus(textDate)
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

}