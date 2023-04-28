package com.example.applicationprototype

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView

class ListItemActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_item)

        val mainNews : TextView = findViewById(R.id.mainText)
        val image : ImageView = findViewById(R.id.image)

        val bundle : Bundle? = intent.extras
        val text = bundle?.getString("EXTRA_DESC")
        val imageId = bundle?.getInt("EXTRA_IMG")

        mainNews.text = text
        if (imageId != null) {
            image.setImageResource(imageId)
        }
    }
}