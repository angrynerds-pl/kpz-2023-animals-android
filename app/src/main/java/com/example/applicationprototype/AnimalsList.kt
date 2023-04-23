package com.example.applicationprototype

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerview.MyAdapter

class AnimalsList : AppCompatActivity() {

    private lateinit var newRecyclerView : RecyclerView
    private lateinit var newArrayList : ArrayList<Animal>
    private lateinit var helpStringList : ArrayList<String>
    private lateinit var descriptions : ArrayList<String>
    lateinit var imageId : Array<Int>
    lateinit var heading : Array<String>
    lateinit var desc : ArrayList<String>

    var ifAdd = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_animals_list)


        ifAdd = intent.getBooleanExtra("EXTRA_BOOLEAN", false)
        if(ifAdd) {
            helpStringList = intent.getStringArrayListExtra("EXTRA_ARRAY") as ArrayList<String>
            descriptions = intent.getStringArrayListExtra("EXTRA_DESC") as ArrayList<String>
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

        desc = arrayListOf(
            getString(R.string.item_a),
            getString(R.string.item_b),
            getString(R.string.item_c),
            getString(R.string.item_d),
            getString(R.string.item_e)
        )


        newRecyclerView = findViewById(R.id.recyclerView)
        newRecyclerView.layoutManager = LinearLayoutManager(this)
        newRecyclerView.setHasFixedSize(true)

        newArrayList = arrayListOf<Animal>()
        getUserData()
    }

    private fun getUserData(){
        for(i in imageId.indices){
            val animal = Animal(imageId[i],heading[i])
            newArrayList.add(animal)
        }
        if(ifAdd){
            for(i in helpStringList){
                val animal = Animal(R.drawable.f, i)
                newArrayList.add(animal)
            }
            for(i in descriptions){
                desc.add(i)
            }
        }
        //newRecyclerView.adapter = MyAdapter(newArrayList)
        var adapter = MyAdapter(newArrayList)
        newRecyclerView.adapter = adapter
        adapter.setOnClickListener(object : MyAdapter.onItemClickListener{
            override fun onItemClick(position: Int) {

              val intent : Intent = Intent(this@AnimalsList,ListItemActivity::class.java)
                intent.putExtra("EXTRA_IMG",newArrayList[position].titleImage)
                intent.putExtra("EXTRA_DESC",desc[position])
                startActivity(intent)
            }

        })


    }


}