package com.example.animalsandroid.trainingListAcivity

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.example.animalsandroid.R

class TrainingListItemActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_training_list_item)

        val name : TextView = findViewById(R.id.trainingNameText)
        val desc : TextView = findViewById(R.id.trainingDescText)
        val image : ImageView = findViewById(R.id.profileImage)
        lateinit var imageId : Bitmap

        val bundle : Bundle? = intent.extras
        if (bundle != null) {
            name.text = bundle.getString("EXTRA_NAME")
            desc.text = bundle.getString("EXTRA_DESC")
        }
        val imageByteArray = bundle?.getByteArray("EXTRA_IMG")
        if (imageByteArray != null && imageByteArray.isNotEmpty()) {
            imageId = BitmapFactory.decodeByteArray(imageByteArray, 0, imageByteArray.size)
            image.setImageBitmap(imageId)
        }
    }
}