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

        var animalPhoto: Bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888)
        val name : TextView = findViewById(R.id.textLostAnimalName)
        val date : TextView = findViewById(R.id.textLostAnimalLostDate)
        val sex : TextView = findViewById(R.id.textLostAnimalSex)
        val chip : TextView = findViewById(R.id.textLostAnimalChip)
        val ownerName : TextView = findViewById(R.id.textLostAnimalOwnerName)
        val phoneNumber : TextView = findViewById(R.id.textLostAnimalPhoneNumber)
        val description : TextView = findViewById(R.id.lostAnimalDescription)
        val breed : TextView = findViewById(R.id.textLostAnimalBreed)
        val color : TextView = findViewById(R.id.textLostAnimalColor)
        val type : TextView = findViewById(R.id.textLostAnimalType)
        val image : ImageView = findViewById(R.id.imageViewLogo)
        lateinit var imageId : Bitmap

        val bundle : Bundle? = intent.extras
        name.text = bundle?.getString("EXTRA_NAME")
        date.text = bundle?.getString("EXTRA_DATE")
        sex.text = bundle?.getString("EXTRA_SEX")
        chip.text = bundle?.getString("EXTRA_CHIP")
        ownerName.text = bundle?.getString("EXTRA_OWNER_NAME")
        phoneNumber.text = bundle?.getString("EXTRA_OWNER_PHONE_NUMBER")
        description.text = bundle?.getString("EXTRA_DESC")
        breed.text = bundle?.getString("EXTRA_BREED")
        color.text = bundle?.getString("EXTRA_COLOR")
        type.text = bundle?.getString("EXTRA_TYPE")

        if (animalPhoto != null) {
            image.setImageBitmap(animalPhoto)
        }
        image.setImageResource(R.drawable.a)
    }
}