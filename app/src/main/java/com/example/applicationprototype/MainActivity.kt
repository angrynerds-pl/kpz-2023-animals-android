package com.example.applicationprototype

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {

    var ifAdd: Boolean = false
    var text: String = ""
    var strings = arrayListOf<String>()
    private lateinit var animalPhoto: ByteArray


    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        findViewById<Button>(R.id.button).setOnClickListener {
            Intent(this, AnimalsList::class.java).also {
                if (ifAdd) {
                    it.putExtra("EXTRA_BOOLEAN", ifAdd)
                    it.putExtra("EXTRA_ARRAY", strings)
                    it.putExtra("EXTRA_JPEG", animalPhoto)
                }
                startActivity(it)
            }
        }

        findViewById<Button>(R.id.button2).setOnClickListener {
                val intent = Intent(this, AddAnimalActivity::class.java)
                startActivityForResult(intent, 1)
        }


            }
    override fun onActivityResult(requestedCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestedCode, resultCode, data)
        if (requestedCode == 1) {
            if (data != null) {
                ifAdd = data.getBooleanExtra("EXTRA_BOOLEAN", false)
                text = data?.getStringExtra("EXTRA_STRING").toString()
                animalPhoto = data?.getByteArrayExtra("EXTRA_JPEG")!!

                strings.add(text)
            }

        }
    }
}


