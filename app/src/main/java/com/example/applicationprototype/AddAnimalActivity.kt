package com.example.applicationprototype

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
import com.example.applicationprototype.databinding.ActivityAddAnimalBinding

class AddAnimalActivity : AppCompatActivity() {
    var pickedPhoto: Uri? = null
    var pickedBitMap: Bitmap? = null
    public lateinit var bilding: ActivityAddAnimalBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bilding = ActivityAddAnimalBinding.inflate(layoutInflater)
        setContentView(bilding.root)
        //setContentView(R.layout.activity_add_animal)
    }
    var textName : String = "a"
    var textLocality : String = "a"
    var textDate : String = "a"
    var concatText : String = "a"

    fun toastMsg(msg:String){
        val toast = Toast.makeText(this,msg,Toast.LENGTH_LONG)
        toast.show()
    }

    fun displayToastMsg(v: View){
        val textView1 = findViewById<EditText>(R.id.editTextName)
        val textView2 = findViewById<EditText>(R.id.editTextLocality)
        val textView3 = findViewById<EditText>(R.id.editTextDate)

        if(textView1.text.toString().isEmpty() || textView2.text.toString().isEmpty() || textView3.text.toString().isEmpty()) {
            toastMsg("Nie podano wszystkich danych")
        }
        else {
            toastMsg("Zatwierdzono")
            getData()
            val intent = Intent()
            intent.putExtra("EXTRA_STRING", concatText)
            intent.putExtra("EXTRA_BOOLEAN", true)
            setResult(1, intent)
            finish()
        }
    }

    fun getData() {
        textName = findViewById<EditText>(R.id.editTextName).text.toString()
        textLocality = findViewById<EditText>(R.id.editTextLocality).text.toString()
        textDate = findViewById<EditText>(R.id.editTextDate).text.toString()
        concatText = textName.plus("\n").plus(textLocality).plus("\n").plus(textDate)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if(grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
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
            pickedPhoto = data.data
            if (Build.VERSION.SDK_INT >= 28){
                val source = ImageDecoder.createSource(this.contentResolver, pickedPhoto!!)
                pickedBitMap = ImageDecoder.decodeBitmap(source)
            }else{
                pickedBitMap = MediaStore.Images.Media.getBitmap(this.contentResolver, pickedPhoto)
            }
            bilding.imageView.setImageBitmap(pickedBitMap)
            bilding.textViewAddPhoto.setText("")
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

}