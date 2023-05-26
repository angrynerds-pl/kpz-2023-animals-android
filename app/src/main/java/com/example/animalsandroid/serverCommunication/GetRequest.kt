package com.example.animalsandroid.serverCommunication

import com.example.animalsandroid.DTO.AnimalColorDTO

class GetRequest {


    fun getAllAnimalColors(): List<AnimalColorDTO> {
        val serverCommunicator = ServerCommunicator()
        return serverCommunicator.getAll("animal-colors", AnimalColorDTO::class.java)
    }
}