package com.example.animalsandroid

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.animalsandroid.DTO.ResponseDTO.AnimalResponseDTO
import com.example.animalsandroid.DTO.ResponseDTO.LostReportResponseDTO
import com.example.animalsandroid.serverCommunication.controllers.AnimalController
import com.example.animalsandroid.serverCommunication.controllers.LostReportController

class AnimalsList : AppCompatActivity() {

    private lateinit var newRecyclerView : RecyclerView
    private var animalPhoto: Bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888)
    private lateinit var newArrayList : ArrayList<LostAnimal>
    private lateinit var helpStringList : ArrayList<String>
    private lateinit var descriptions : ArrayList<String>
    private lateinit var animalsList : List<LostReportResponseDTO>
    //lateinit var imageId : Array<Int>
    //lateinit var heading : Array<String>
    //lateinit var desc : ArrayList<String>
    //lateinit var byteArray : ByteArray

    var ifAdd = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_animals_list)

        var lostController = LostReportController()
        animalsList = lostController.getAllLostReports()


        newRecyclerView = findViewById(R.id.recyclerView)
        newRecyclerView.layoutManager = LinearLayoutManager(this)
        newRecyclerView.setHasFixedSize(true)

        newArrayList = arrayListOf<LostAnimal>()
        getUserData()
    }

    private fun getUserData(){
            for(i in animalsList){
                val animal = LostAnimal(animalPhoto, i.animal.name, i.lostDate.removeRange(10,i.lostDate.length))
                newArrayList.add(animal)
        }
        //newRecyclerView.adapter = MyAdapter(newArrayList)
        var adapter = LostAnimalAdapter(newArrayList)
        newRecyclerView.adapter = adapter
        adapter.setOnClickListener(object : LostAnimalAdapter.onItemClickListener{
            override fun onItemClick(position: Int) {

              val intent : Intent = Intent(this@AnimalsList,ListItemActivity::class.java)
                intent.putExtra("EXTRA_IMG",animalPhoto)
                intent.putExtra("EXTRA_DATE", animalsList[position].lostDate)
                intent.putExtra("EXTRA_NAME", animalsList[position].animal.name)
                intent.putExtra("EXTRA_CHIP", animalsList[position].animal.sex)
                intent.putExtra("EXTRA_OWNER_NAME", animalsList[position].animal.ownerName)
                intent.putExtra("EXTRA_OWNER_PHONE_NUMBER", animalsList[position].animal.ownerPhoneNumber)
                intent.putExtra("EXTRA_DESC", animalsList[position].description)
                intent.putExtra("EXTRA_BREED", animalsList[position].animal.breed.name)
                intent.putExtra("EXTRA_COLOR", animalsList[position].animal.color.name)
                intent.putExtra("EXTRA_TYPE", animalsList[position].animal.breed.type.name)
                startActivity(intent)
            }

        })


    }
}