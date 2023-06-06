package com.example.animalsandroid

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView

class ProfileListItemActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_list_item)

        val name : TextView = findViewById(R.id.textName)
        val species : TextView = findViewById(R.id.textSpecies)
        val breed : TextView = findViewById(R.id.textBreed)
        val gender : TextView = findViewById(R.id.textGender)
        val chip : TextView = findViewById(R.id.textChip)
        val image : ImageView = findViewById(R.id.profileImage)
        lateinit var imageId : Bitmap

        val bundle : Bundle? = intent.extras
        if (bundle != null) {
            name.text = bundle.getString("EXTRA_NAME")
            println(name.text)
            species.text = bundle.getString("EXTRA_SPECIES")
            breed.text = bundle.getString("EXTRA_BREED")
            gender.text = bundle.getString("EXTRA_GENDER")
            chip.text = bundle.getString("EXTRA_CHIP")
        }
        val imageByteArray = bundle?.getByteArray("EXTRA_IMG")
        if (imageByteArray != null && imageByteArray.isNotEmpty()) {
            imageId = BitmapFactory.decodeByteArray(imageByteArray, 0, imageByteArray.size)
            //image.setImageBitmap(imageId)
        }
        image.setImageResource(R.drawable.a)
    }
}