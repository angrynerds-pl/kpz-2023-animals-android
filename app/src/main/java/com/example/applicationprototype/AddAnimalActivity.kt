package com.example.applicationprototype

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast

class AddAnimalActivity : AppCompatActivity() {

    var text1 : String = "a"
    var text2 : String = "a"
    var text3 : String = "a"
    var concatText : String = "a"
    var description : String = "a"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_animal)
    }


    fun toastMsg(msg:String){
        val toast = Toast.makeText(this,msg,Toast.LENGTH_LONG)
        toast.show()
    }

    fun displayToastMsg(v: View){
        val textView1 = findViewById<EditText>(R.id.editText)
        val textView2 = findViewById<EditText>(R.id.editText2)
        val textView3 = findViewById<EditText>(R.id.editText3)

        if(textView1.text.toString().isEmpty() || textView2.text.toString().isEmpty() || textView3.text.toString().isEmpty()) {
            toastMsg("Nie podano wszystkich danych")
        }
        else {
            toastMsg("Zatwierdzono")
            getData()
            val intent = Intent()
            intent.putExtra("EXTRA_STRING", concatText)
            intent.putExtra("EXTRA_BOOLEAN", true)
            intent.putExtra("EXTRA_DESC", description)
            setResult(1, intent)
            finish()
        }
    }

    fun getData() {
        text1 = findViewById<EditText>(R.id.editText).text.toString()
        text2 = findViewById<EditText>(R.id.editText2).text.toString()
        text3 = findViewById<EditText>(R.id.editText3).text.toString()
        concatText = text1.plus("\n").plus(text2).plus("\n").plus(text3)
        description = findViewById<EditText>(R.id.editTextTextMultiLine).text.toString()
    }

}