package com.example.animalsandroid.serverCommunication.controllers

import com.example.animalsandroid.DTO.AnimalPictureDTO
import com.example.animalsandroid.DTO.AnimalSex
import com.example.animalsandroid.DTO.RequestDTO.AnimalRequestDTO
import com.example.animalsandroid.DTO.ResponseDTO.AnimalResponseDTO
import com.example.animalsandroid.serverCommunication.ServerCommunicator
import com.fasterxml.jackson.databind.ObjectMapper

class AnimalController {

    fun getAllAnimals(): List<AnimalResponseDTO> {
        val serverCommunicator = ServerCommunicator()
        return serverCommunicator.getAll("animals", AnimalResponseDTO::class.java)
    }


    fun postAnimal(name: String, chip: String?, sex: AnimalSex, ownerId: Int, animalColorId: Int, breedId: Int, animalPhoto: ByteArray){
            val animalRequestDTO = AnimalRequestDTO(name, chip, sex, ownerId, animalColorId, breedId)
            val serverCommunicator = ServerCommunicator ()
            serverCommunicator.post("animals", AnimalRequestDTO::class.java, animalRequestDTO) { responseBody ->
                if(responseBody != null){
                    val objectMapper = ObjectMapper()
                    val animalResponse = objectMapper.readValue(responseBody, AnimalResponseDTO ::class.java)

                    val animalController = AnimalController()
                    animalController.postAnimalPicture(animalResponse.id, animalPhoto)
                }
            }
    }

    fun getAnimalPicture(animalId : Int): List<AnimalPictureDTO> {
        val serverCommunicator = ServerCommunicator()
        return serverCommunicator.getAll("animals/$animalId/pictures", AnimalPictureDTO::class.java)
    }

    fun postAnimalPicture(animalId : Int, imageBytes : ByteArray){
        val serverCommunicator = ServerCommunicator()
        serverCommunicator.postPicture("animals/$animalId/pictures", imageBytes)
    }
}