package com.example.animalsandroid

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView

class ListItemActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_item)

        val mainNews : TextView = findViewById(R.id.mainText)
        val image : ImageView = findViewById(R.id.imageViewLogo)
        lateinit var imageId : Bitmap

        val bundle : Bundle? = intent.extras
        val text = bundle?.getString("EXTRA_DESC")
        //val imageId = bundle?.getInt("EXTRA_IMG")
        val imageByteArray = bundle?.getByteArray("EXTRA_IMG")
        if (imageByteArray != null && imageByteArray.isNotEmpty()) {
            imageId = BitmapFactory.decodeByteArray(imageByteArray, 0, imageByteArray.size)
        }

        mainNews.text = text
        if (imageId != null) {
            image.setImageBitmap(imageId)
        }
    }
}