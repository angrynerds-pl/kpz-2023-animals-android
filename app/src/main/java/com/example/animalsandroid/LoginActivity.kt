package com.example.animalsandroid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast

class LoginActivity : AppCompatActivity() {

    lateinit var login : String
    lateinit var password : String
    var logged = 0

    val user1 = User("login1", "password1")
    val user2 = User("login2", "password2")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }

    private fun toastMsg(msg:String){
        val toast = Toast.makeText(this,msg, Toast.LENGTH_LONG)
        toast.show()
    }

    fun loginButton(view : View) {
        getData()
        if(login == "" || password == ""){
            toastMsg("Nie podano wszystkich danych")
        }
        if(login == "login1" && password == "password1") {
            logged = 1
            toastMsg("Zalogowano")
            sendData()
        }
        else if(login == "login2" && password == "password2"){
            logged = 2
            toastMsg("Zalogowano")
            sendData()
        }
        else
            toastMsg("Niepoprawne dane logowania")
    }

    private fun getData(){
        login = findViewById<EditText>(R.id.login).text.toString()
        password = findViewById<EditText>(R.id.password).text.toString()
    }

    private fun sendData(){
        val intent = Intent()
        intent.putExtra("EXTRA_LOGIN", logged)
        setResult(2, intent)
        finish()
    }
}