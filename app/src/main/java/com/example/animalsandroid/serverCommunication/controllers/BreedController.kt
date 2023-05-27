package com.example.animalsandroid.serverCommunication.controllers

import com.example.animalsandroid.DTO.BreedDTO
import com.example.animalsandroid.DTO.TypeDTO
import com.example.animalsandroid.serverCommunication.ServerCommunicator

class BreedController {

    fun getAllBreeds(): List<BreedDTO> {
        val serverCommunicator = ServerCommunicator()
        return serverCommunicator.getAll("breeds", BreedDTO::class.java)
    }

    //postBreed do usuniecia ze wgledu na bezpieczenstwo
    fun postBreed(name : String, type : TypeDTO){
        val breedDto = BreedDTO(0, name = name, type = type)

        val serverCommunicator = ServerCommunicator ()
        serverCommunicator.post("breeds", BreedDTO::class.java, breedDto)
    }
}