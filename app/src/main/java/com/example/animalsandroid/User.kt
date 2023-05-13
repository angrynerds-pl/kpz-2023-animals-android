package com.example.animalsandroid

class User(private var login : String, private var password : String) {

    fun getLogin() : String {
        return login
    }

    fun getPassword() : String {
        return password
    }
}