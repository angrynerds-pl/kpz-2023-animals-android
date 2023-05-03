package com.example.animalsandroid

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ProfileListActivity : AppCompatActivity() {

    private lateinit var newRecyclerView : RecyclerView
    private var animalPhoto: Bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888)
    private lateinit var newArrayList : ArrayList<Animal>
    private lateinit var helpStringList : ArrayList<String>
    private lateinit var descriptions : ArrayList<String>
    lateinit var names : ArrayList<String>
    lateinit var species : ArrayList<String>
    lateinit var breeds : ArrayList<String>
    lateinit var genders : ArrayList<String>
    lateinit var chips : ArrayList<String>
    lateinit var byteArray : ByteArray

    var ifAdd = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_list)

        names = arrayListOf()
        species = arrayListOf()
        breeds = arrayListOf()
        genders = arrayListOf()
        chips = arrayListOf()

        newRecyclerView = findViewById(R.id.mRecycler)
        newRecyclerView.layoutManager = LinearLayoutManager(this)
        newRecyclerView.setHasFixedSize(true)

        newArrayList = arrayListOf<Animal>()
        getUserData()
    }

    fun getUserData() {
        if (ifAdd) {
            for (i in names) {
                val animal = Animal(animalPhoto, i)
                newArrayList.add(animal)
            }
            //for (i in descriptions) {
            //    desc.add(i)
            //}
        }
        var adapter = MyAdapter(newArrayList)
        newRecyclerView.adapter = adapter

        adapter.setOnClickListener(object : MyAdapter.onItemClickListener {
            override fun onItemClick(position: Int) {

                val intent: Intent = Intent(this@ProfileListActivity, ProfileListItemActivity::class.java)
                intent.putExtra("EXTRA_IMG", byteArray)
                intent.putExtra("EXTRA_NAME",names[position])
                intent.putExtra("EXTRA_SPECIES", species[position])
                intent.putExtra("EXTRA_BREED", breeds[position])
                intent.putExtra("EXTRA_GENDER", genders[position])
                intent.putExtra("EXTRA_CHIP", chips[position])
                startActivity(intent)
            }

        })
    }

    fun addAnimalProfile(view : View){
        val intent = Intent(this, AddProfileActivity::class.java)
        startActivityForResult(intent, 1)
        }

    override fun onActivityResult(requestedCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestedCode, resultCode, data)
        if (requestedCode == 1) {
            if (data != null) {
                ifAdd = data.getBooleanExtra("EXTRA_BOOLEAN", false)
                var name = data.getStringExtra("EXTRA_NAME").toString()
                names.add(name)
                var specie = data.getStringExtra("EXTRA_SPECIES").toString()
                species.add(specie)
                var breed = data.getStringExtra("EXTRA_BREED").toString()
                breeds.add(breed)
                var gender = data.getStringExtra("EXTRA_GENDER").toString()
                genders.add(gender)
                var chip = data.getStringExtra("EXTRA_CHIP").toString()
                chips.add(chip)
                byteArray = data.getByteArrayExtra("EXTRA_JPEG")!!
                if (byteArray != null && byteArray.isNotEmpty()) {
                    animalPhoto = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
                }
            }
            getUserData()

        }
    }

}