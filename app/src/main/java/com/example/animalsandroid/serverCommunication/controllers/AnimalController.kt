package com.example.animalsandroid.serverCommunication.controllers

import com.example.animalsandroid.DTO.BreedDTO
import com.example.animalsandroid.DTO.AnimalSex
import com.example.animalsandroid.DTO.RequestDTO.AnimalRequestDTO
import com.example.animalsandroid.DTO.ResponseDTO.AnimalResponseDTO
import com.example.animalsandroid.DTO.TypeDTO
import com.example.animalsandroid.serverCommunication.ServerCommunicator

class AnimalController {

    fun getAllAnimals(): List<AnimalResponseDTO> {
        val serverCommunicator = ServerCommunicator()
        return serverCommunicator.getAll("animals", AnimalResponseDTO::class.java)
    }


    fun postAnimal(name : String, chip : String, sex : AnimalSex, ownerId : Int, animalColorId : Int, breedId : Int){
            val animalRequestDTO = AnimalRequestDTO(name, chip, sex, ownerId, animalColorId, breedId)
            val serverCommunicator = ServerCommunicator ()
            serverCommunicator.post("animals", AnimalRequestDTO::class.java, animalRequestDTO)
    }

    fun getAnimalById(id : Int): AnimalResponseDTO {
        val serverCommunicator = ServerCommunicator()
        return serverCommunicator.get("animals/$id", AnimalResponseDTO::class.java)
    }
}