package com.example.animalsandroid.serverCommunication

import okhttp3.MediaType.Companion.toMediaType
import com.fasterxml.jackson.databind.ObjectMapper
import okhttp3.*
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import java.io.IOException

class ServerCommunicator() {
    private val serverAddress = "http://10.0.2.2:8080/"

    fun post(endpoint : String, json : String) : Boolean {
        val requestBody = json.toRequestBody("application/json".toMediaType())
        val request = Request.Builder()
            .url(serverAddress + endpoint)
            .post(requestBody)
            .build()

        return callRequest(request) != null
    }

    fun get(endpoint: String) : String?{
        val request = Request.Builder()
            .url(serverAddress + endpoint)
            .build()

        return callRequest(request)
    }

    private fun callRequest(request: Request) : String?{
        val client = OkHttpClient()

        var responseBody : String? = null
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                //print("błąd\n")
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    responseBody = response.body?.string()
                    //print("udało się\n")
                } else {
                    //print("nie udało się\n")
                }
            }
        })
        return responseBody;
    }

    fun <T> getAll(endpoint: String, itemType: Class<T>): List<T> {
        val client = OkHttpClient()
        val request = Request.Builder()
            .url(serverAddress + endpoint)
            .build()

        val objectMapper = ObjectMapper()

        val response = client.newCall(request).execute()
        if (response.isSuccessful) {
            val responseBody = response.body?.string()
            try {
                val type = objectMapper.typeFactory.constructCollectionType(List::class.java, itemType)
                return objectMapper.readValue(responseBody, type)
            } catch (e: IOException) {
                throw e
            }
        } else {
            throw Exception("Request unsuccessful: ${response.code}")
        }
    }



}