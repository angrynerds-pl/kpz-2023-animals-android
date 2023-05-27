package com.example.animalsandroid.serverCommunication.controllers

import com.example.animalsandroid.DTO.AnimalColorDTO
import com.example.animalsandroid.serverCommunication.ServerCommunicator

class AnimalColorController {


    fun getAllAnimalColors(): List<AnimalColorDTO> {
        val serverCommunicator = ServerCommunicator()
        return serverCommunicator.getAll("animal-colors", AnimalColorDTO::class.java)
    }

    //postAnimalColor do usuniecia ze wgledu na bezpieczenstwo
    fun postAnimalColor(name : String){
        val colorDto = AnimalColorDTO(0, name = name)

        val serverCommunicator = ServerCommunicator ()
        serverCommunicator.post("animal-colors", AnimalColorDTO::class.java, colorDto)
    }
}