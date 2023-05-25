package com.example.animalsandroid.trainingListAcivity

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.animalsandroid.AddTrainingActivity
import com.example.animalsandroid.R

class TrainingListActivity : AppCompatActivity() {

    lateinit var names : ArrayList<String>
    private lateinit var newRecyclerView : RecyclerView
    private lateinit var newArrayList : ArrayList<Training>
    private var trainingPhoto: Bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888)
    private lateinit var descriptions : ArrayList<String>
    lateinit var byteArray : ByteArray

    var ifAdd = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_training_list)

        names = arrayListOf()
        descriptions = arrayListOf()

        newRecyclerView = findViewById(R.id.mRecycler1)
        newRecyclerView.layoutManager = LinearLayoutManager(this)
        newRecyclerView.setHasFixedSize(true)

        newArrayList = arrayListOf<Training>()
        getUserData()
    }

    fun getUserData(){
        if (ifAdd) {
            newArrayList.clear()
            for (i in names) {
                val training = Training(trainingPhoto, i)
                newArrayList.add(training)
            }
        }

        var adapter = TrainingAdapter(newArrayList)
        newRecyclerView.adapter = adapter

        adapter.setOnClickListener(object : TrainingAdapter.onItemClickListener {
            override fun onItemClick(position: Int) {

                val intent: Intent = Intent(this@TrainingListActivity, TrainingListItemActivity::class.java)
                intent.putExtra("EXTRA_IMG", byteArray)
                intent.putExtra("EXTRA_NAME",names[position])
                intent.putExtra("EXTRA_DESC", descriptions[position])
                startActivity(intent)
            }

        })
    }
    fun addTraining(view : View){
        val intent = Intent(this, AddTrainingActivity::class.java)
        startActivityForResult(intent, 1)
    }

    override fun onActivityResult(requestedCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestedCode, resultCode, data)
        if (requestedCode == 1) {
            if (data != null) {
                ifAdd = data.getBooleanExtra("EXTRA_BOOLEAN", false)
                var name = data.getStringExtra("EXTRA_NAME").toString()
                names.add(name)
                var description = data.getStringExtra("EXTRA_DESC").toString()
                descriptions.add(description)
                byteArray = data.getByteArrayExtra("EXTRA_JPEG")!!
                if (byteArray != null && byteArray.isNotEmpty()) {
                    trainingPhoto = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
                }
            }
            getUserData()

        }
    }
}