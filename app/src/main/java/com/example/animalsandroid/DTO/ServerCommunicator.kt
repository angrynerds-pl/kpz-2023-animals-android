package com.example.animalsandroid.DTO

import okhttp3.MediaType.Companion.toMediaType
import com.fasterxml.jackson.databind.ObjectMapper
import okhttp3.*
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.IOException

class ServerCommunicator(val endpoint : String, val json : String) {


    fun post() {
        val requestBody = json.toRequestBody("application/json".toMediaType())
        val request = Request.Builder()
            .url(endpoint)
            .post(requestBody)
            .build()

        val client = OkHttpClient()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                // Obsługa błędu w przypadku niepowodzenia zapytania
                print("błąd\n")
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    val responseBody = response.body?.string()
                    // Odpowiedź serwera powiodła się, odczytaj odpowiedź
                    print("udało się\n")
                } else {
                    // Obsługa błędu w przypadku nieudanej odpowiedzi
                    print("nie udało się\n")
                }
            }
        })
    }
}