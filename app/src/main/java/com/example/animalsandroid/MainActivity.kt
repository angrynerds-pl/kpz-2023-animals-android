package com.example.animalsandroid

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.StrictMode
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import okhttp3.*
import java.io.IOException

class MainActivity : AppCompatActivity() {



    var ifAdd: Boolean = false
    var text: String = ""
    var strings = arrayListOf<String>()
    var descriptions = arrayListOf<String>()
    private lateinit var animalPhoto: ByteArray
    var logged = 0

    val client = OkHttpClient()


    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)
        testHttp()
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.app_bar_menu,menu)
        return true
    }

    fun testHttp() {
        var request = Request.Builder().url("http://10.0.2.2:8080/users").build()
        val call = client.newCall(request)
        val response = call.execute()
        println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXxx")
        println(response.body.toString())
        println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX")
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.miLogin -> {
                val intent = Intent( this, LoginActivity::class.java)
                startActivityForResult(intent, 2)
            }
            R.id.miLogout -> {
                if(logged == 0) {
                    val toast = Toast.makeText(this, "Nie jesteś zalogowany", Toast.LENGTH_LONG)
                    toast.show()
                }
                else{
                    val toast = Toast.makeText(this, "Wylogowano", Toast.LENGTH_LONG)
                    toast.show()
                }
                logged = 0
            }
        }
        return true
    }

    fun onClickReportMissing(view: View){
        if(logged == 0){
            val toast = Toast.makeText(this,"Wymaga zalogowania", Toast.LENGTH_LONG)
            toast.show()
        }
        else {
            val intent = Intent(this, AddMissingReportActivity::class.java)
            startActivityForResult(intent, 1)
        }
    }

    fun onClickReportFound(view: View){
        if(logged == 0){
            val toast = Toast.makeText(this,"Wymaga zalogowania", Toast.LENGTH_LONG)
            toast.show()
        }
        else {
            val intent = Intent(this, AddFoundReportActivity::class.java)
            startActivity(intent)
        }
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
        if(logged == 0){
            val toast = Toast.makeText(this,"Wymaga zalogowania", Toast.LENGTH_LONG)
            toast.show()
        }
        else {
            Intent(this, ProfileListActivity::class.java).also {
                startActivity(it)
            }
        }
    }

    fun onClickTrainingList(view: View){
        Intent(this, TrainingListActivity::class.java).also{
            startActivity(it)
        }
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
        if(requestedCode == 2){
            if(data != null){
                logged = data.getIntExtra("EXTRA_LOGIN",0)
                println(logged)
            }
        }
    }
}


