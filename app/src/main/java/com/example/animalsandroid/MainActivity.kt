package com.example.animalsandroid

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class MainActivity : AppCompatActivity() {

    var ifAdd: Boolean = false
    var text: String = ""
    var strings = arrayListOf<String>()
    var descriptions = arrayListOf<String>()
    private lateinit var animalPhoto: ByteArray


    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun onClickReportMissing(view: View){
        val intent = Intent(this, AddMissingReportActivity::class.java)
        startActivityForResult(intent, 1)
    }

    fun onClickReportFound(view: View){
        val intent = Intent(this, AddFoundReportActivity::class.java)
        startActivity(intent)
    }

    fun onClickMissingList(view: View){
        Intent(this, AnimalsList::class.java).also {
            if (ifAdd) {
                it.putExtra("EXTRA_BOOLEAN", ifAdd)
                it.putExtra("EXTRA_ARRAY", strings)
                it.putExtra("EXTRA_JPEG", animalPhoto)
                it.putExtra("EXTRA_DESC", descriptions)
            }
            startActivity(it)
        }
    }

    fun onClickAnimalProfile(view: View){
        Intent(this, ProfileListActivity::class.java).also {
            startActivity(it)
        }
    }

    fun onClickTrainingList(view: View){

    }

    fun onClickVeterinaryList(view: View){
        val intent = Intent(this, VeterinaryMapActivity::class.java)
        startActivity(intent)
    }

    override fun onActivityResult(requestedCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestedCode, resultCode, data)
        if (requestedCode == 1) {
            if (data != null) {
                ifAdd = data.getBooleanExtra("EXTRA_BOOLEAN", false)
                text = data?.getStringExtra("EXTRA_STRING").toString()
                animalPhoto = data?.getByteArrayExtra("EXTRA_JPEG")!!

                strings.add(text)
                text = data?.getStringExtra("EXTRA_DESC").toString()
                descriptions.add(text)
            }

        }
    }
}


