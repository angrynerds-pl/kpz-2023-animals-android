package com.example.animalsandroid

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
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
import com.example.animalsandroid.databinding.ActivityAddAnimalBinding
import java.io.ByteArrayOutputStream

class AddAnimalActivity : AppCompatActivity() {

    private lateinit var text1 : String
    private lateinit var text2 : String
    private lateinit var text3 : String
    private lateinit var concatText : String
    private lateinit var description : String

    private lateinit var pickedBitMap: Bitmap
    private lateinit var bilding: ActivityAddAnimalBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bilding = ActivityAddAnimalBinding.inflate(layoutInflater)
        setContentView(bilding.root)
        //setContentView(R.layout.activity_add_animal)
    }


    fun toastMsg(msg:String){
        val toast = Toast.makeText(this,msg,Toast.LENGTH_LONG)
        toast.show()
    }

    fun displayToastMsg(v: View){
        val textView1 = findViewById<EditText>(R.id.editText)
        val textView2 = findViewById<EditText>(R.id.editText2)
        val textView3 = findViewById<EditText>(R.id.editText3)

        if(textView1.text.toString().isEmpty() || textView2.text.toString().isEmpty()
            || textView3.text.toString().isEmpty() || pickedBitMap == null) {
            toastMsg("Nie podano wszystkich danych")
        }
        else {
            toastMsg("Zatwierdzono")
            getData()
            val intent = Intent()
            intent.putExtra("EXTRA_STRING", concatText)
            intent.putExtra("EXTRA_BOOLEAN", true)
            intent.putExtra("EXTRA_DESC", description)
            intent.putExtra("EXTRA_JPEG", compressBitmap(pickedBitMap))
            setResult(1, intent)
            finish()
        }
    }

    fun getData() {
        text1 = findViewById<EditText>(R.id.editText).text.toString()
        text2 = findViewById<EditText>(R.id.editText2).text.toString()
        text3 = findViewById<EditText>(R.id.editText3).text.toString()
        concatText = text1.plus("\n").plus(text2).plus("\n").plus(text3)
        description = findViewById<EditText>(R.id.editTextTextMultiLine).text.toString()
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