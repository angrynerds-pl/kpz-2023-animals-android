package com.example.animalsandroid.serverCommunication

import okhttp3.MediaType.Companion.toMediaType
import com.fasterxml.jackson.databind.ObjectMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import java.io.IOException

class ServerCommunicator() {
    private val serverAddress = "http://10.0.2.2:8080/"

    fun <E> post(endpoint : String, itemType: Class<E>, item: E, callback: ((String?) -> Unit)? = null) {
        val objectMapper = ObjectMapper()
        val json = objectMapper.writeValueAsString(item)
        val requestBody = json.toRequestBody("application/json".toMediaType())
        println(json)

        val request = Request.Builder()
            .url(serverAddress + endpoint)
            .post(requestBody)
            .build()

        callRequest(request) { responseBody ->
            callback?.invoke(responseBody)
        }
    }

    fun <T> get(endpoint: String, itemType: Class<T>): T {
        val client = OkHttpClient()
        val request = Request.Builder()
            .url(serverAddress + endpoint)
            .build()

        val objectMapper = ObjectMapper()

        val response = client.newCall(request).execute()
        if (response.isSuccessful) {
            val responseBody = response.body?.string()
            try {
                return objectMapper.readValue(responseBody, itemType)
            } catch (e: IOException) {
                throw e
            }
        } else {
            throw Exception("Request unsuccessful: ${response.code}")
        }
    }


    fun callRequest(request: Request, callback: (String?) -> Unit){
        val client = OkHttpClient()

        var responseBody : String? = null
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                print("błąd\n")
                callback(null)
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    responseBody = response.body?.string()
                    print("udało się\n")
                    callback(responseBody)
                } else {
                    print("nie udało się\n")
                    callback(null)
                }
            }
        })
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

    fun postPicture(endpoint: String, imageBytes : ByteArray, callback: ((String?) -> Unit)? = null){
        val mediaType = "image/jpeg".toMediaTypeOrNull()
        val requestBody = RequestBody.create(mediaType, imageBytes)

        val builder = MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart("files", "image.jpg", requestBody)

        val multipartBody = builder.build()

        val request = Request.Builder()
            .url(serverAddress + endpoint)
            .post(multipartBody)
            .build()

        callRequest(request) { responseBody ->
            callback?.invoke(responseBody)
        }
    }

}