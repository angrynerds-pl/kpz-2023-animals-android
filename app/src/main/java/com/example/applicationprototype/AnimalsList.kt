package com.example.applicationprototype

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerview.MyAdapter

class AnimalsList : AppCompatActivity() {

    private lateinit var newRecyclerView : RecyclerView
    private var animalPhoto: Bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888)
    private lateinit var newArrayList : ArrayList<Animal>
    private lateinit var helpStringList: ArrayList<String>
    lateinit var imageId : Array<Int>
    lateinit var heading : Array<String>

    var ifAdd = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_animals_list)

        //Log.d("SPRAWDZENIE", " Stworzenie")

        ifAdd = intent.getBooleanExtra("EXTRA_BOOLEAN", false)
        if(ifAdd) {
            helpStringList = intent.getStringArrayListExtra("EXTRA_ARRAY") as ArrayList<String>
            val byteArray = intent.getByteArrayExtra("EXTRA_PNG")
            if (byteArray != null && byteArray.isNotEmpty()) {
                animalPhoto = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
            }
        }


        imageId = arrayOf(
            R.drawable.a,
            R.drawable.b,
            R.drawable.c,
            R.drawable.d,
            R.drawable.e
        )


        heading = arrayOf(
            "Bella\nWrocław\n25.03.2023",
            "Bella\nWrocław\n30.03.2023",
            "Pumba\nOpole\n2.04.2023",
            "Pumba\nOpole\n1.04.2023",
            "Hektor\nKamieniec\n3.04.2022"
        )


        newRecyclerView = findViewById(R.id.recyclerView)
        newRecyclerView.layoutManager = LinearLayoutManager(this)
        newRecyclerView.setHasFixedSize(true)

        newArrayList = arrayListOf<Animal>()
        getUserData()
    }

    private fun getUserData(){
        //for(i in imageId.indices){
        //    val animal = Animal(imageId[i],heading[i])
        //    newArrayList.add(animal)
        //}
        if(ifAdd){
            for(i in helpStringList){
                val animal = Animal(animalPhoto, i)
                newArrayList.add(animal)
            }
        }

        newRecyclerView.adapter = MyAdapter(newArrayList)
    }
}