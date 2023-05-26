package com.example.animalsandroid.serverCommunication

import com.example.animalsandroid.DTO.AnimalColorDTO
import com.fasterxml.jackson.databind.ObjectMapper

class PostRequest {


    fun postAnimalColor(name : String){
        val colorDto = AnimalColorDTO(0, name = name)

        val objectMapper = ObjectMapper()
        val json = objectMapper.writeValueAsString(colorDto)

        val serverCommunicator = ServerCommunicator ()
        serverCommunicator.post("animal-colors", json)
    }
}