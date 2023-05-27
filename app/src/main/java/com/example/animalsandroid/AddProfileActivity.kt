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
import com.example.animalsandroid.databinding.ActivityAddLostReportBinding
import com.example.animalsandroid.databinding.ActivityAddProfileBinding
import java.io.ByteArrayOutputStream

class AddProfileActivity : AppCompatActivity() {

    private lateinit var name : String
    private lateinit var species : String
    private lateinit var breed : String
    private lateinit var gender : String
    private lateinit var chipNumber : String

    private lateinit var pickedBitMap: Bitmap
    private lateinit var bilding: ActivityAddProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bilding = ActivityAddProfileBinding.inflate(layoutInflater)
        setContentView(bilding.root)
    }

    private fun toastMsg(msg:String){
        val toast = Toast.makeText(this,msg, Toast.LENGTH_LONG)
        toast.show()
    }

    fun displayToastMsg(v: View){

        getData()
        if(name == "" || species == "" || breed == "" || gender == "" || pickedBitMap == null) {
            toastMsg("Nie podano wszystkich danych")
        }
        else {
            toastMsg("Zatwierdzono")
            sendData()

        }
    }

    fun getData(){
        name = findViewById<EditText>(R.id.Name).text.toString()
        species = findViewById<EditText>(R.id.Species).text.toString()
        breed = findViewById<EditText>(R.id.Breed).text.toString()
        gender = findViewById<EditText>(R.id.Gender).text.toString()
        chipNumber = findViewById<EditText>(R.id.Chip).text.toString()
    }

    fun sendData(){
        val intent = Intent()
        intent.putExtra("EXTRA_BOOLEAN", true)
        intent.putExtra("EXTRA_NAME", name)
        intent.putExtra("EXTRA_SPECIES", species)
        intent.putExtra("EXTRA_BREED", breed)
        intent.putExtra("EXTRA_GENDER", gender)
        intent.putExtra("EXTRA_CHIP", chipNumber)
        intent.putExtra("EXTRA_JPEG", compressBitmap(pickedBitMap))
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
}