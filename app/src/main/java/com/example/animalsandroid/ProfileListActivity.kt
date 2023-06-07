package com.example.animalsandroid

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.animalsandroid.DTO.ResponseDTO.AnimalResponseDTO
import com.example.animalsandroid.DTO.TypeDTO
import com.example.animalsandroid.serverCommunication.controllers.AnimalController
import com.example.animalsandroid.serverCommunication.controllers.UserController
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL

class ProfileListActivity : AppCompatActivity() {

    private lateinit var newRecyclerView : RecyclerView
    //private var animalPhoto: Bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888)
    private lateinit var newArrayList : ArrayList<Animal>
    private lateinit var animalsList : List<AnimalResponseDTO>



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_list)

        var userController = UserController()
        animalsList = userController.getUserAnimals(1) //userId jest hardcodowane

        newRecyclerView = findViewById(R.id.mRecycler)
        newRecyclerView.layoutManager = LinearLayoutManager(this)
        newRecyclerView.setHasFixedSize(true)

        newArrayList = arrayListOf<Animal>()
        getUserData()
    }


    fun getUserData() {
//            newArrayList.clear()
            for (i in animalsList) {
                //Animal(null,i.name) <- zeby nie pobieralo zdjec trzeba to odkomentować i zakomentowac calego if'a ponizej

                val animalController = AnimalController()
                val pictures = animalController.getAnimalPicture(i.id)

                val animal = if(pictures.isNotEmpty()){
                    Animal(animalController.getAnimalPicture(i.id).get(0).url,i.name)
                }else{
                    Animal(null,i.name)
                }

                newArrayList.add(animal)
            }
            //for (i in descriptions) {
            //    desc.add(i)
            //}
        var adapter = MyAdapter(newArrayList)
        newRecyclerView.adapter = adapter

        adapter.setOnClickListener(object : MyAdapter.onItemClickListener {
            override fun onItemClick(position: Int) {

                val intent: Intent = Intent(this@ProfileListActivity, ProfileListItemActivity::class.java)
                //intent.putExtra("EXTRA_IMG", animalPhoto)
                intent.putExtra("EXTRA_NAME",animalsList[position].name)
                intent.putExtra("EXTRA_SPECIES", animalsList[position].breed.type.name)
                intent.putExtra("EXTRA_BREED", animalsList[position].breed.name)
                intent.putExtra("EXTRA_GENDER", animalsList[position].sex.name)
                intent.putExtra("EXTRA_CHIP", animalsList[position].chip)
                startActivity(intent)
            }
        })
    }



    fun addAnimalProfile(view : View){
        val intent = Intent(this, AddProfileActivity::class.java)
        startActivity(intent)
    }

    fun convertUrlToByteArray(urlString: String): ByteArray {
        val url = URL(urlString)
        val connection = url.openConnection()
        val inputStream = connection.getInputStream()
        return inputStream.readBytes()
    }

}




