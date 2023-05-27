package com.example.animalsandroid.serverCommunication.controllers

import com.example.animalsandroid.DTO.ResponseDTO.AnimalResponseDTO
import com.example.animalsandroid.serverCommunication.ServerCommunicator

class AnimalController {

    fun getAllAnimals(): List<AnimalResponseDTO> {
        val serverCommunicator = ServerCommunicator()
        return serverCommunicator.getAll("animals", AnimalResponseDTO::class.java)
    }

    fun getAnimalById(id : Int){

    }
}