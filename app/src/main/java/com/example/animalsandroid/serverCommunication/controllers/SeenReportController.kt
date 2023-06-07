package com.example.animalsandroid.serverCommunication.controllers

import com.example.animalsandroid.DTO.CoordinateDTO
import com.example.animalsandroid.DTO.RequestDTO.AnimalRequestDTO
import com.example.animalsandroid.DTO.RequestDTO.SeenReportRequestDTO
import com.example.animalsandroid.DTO.ResponseDTO.SeenReportResponseDTO
import com.example.animalsandroid.serverCommunication.ServerCommunicator
import com.fasterxml.jackson.databind.ObjectMapper

class SeenReportController {

    fun getAllSeenReports(): List<SeenReportResponseDTO> {
        val serverCommunicator = ServerCommunicator()
        return serverCommunicator.getAll("seen", SeenReportResponseDTO::class.java)
    }

    fun postSeenReport(lostDate : String, coordinate: CoordinateDTO, description : String, userId : Int, animalId : Int?, animalPhoto : ByteArray, animalRequest: AnimalRequestDTO){
        val seenReportDto = SeenReportRequestDTO(lostDate = lostDate, coordinate = coordinate, description = description,
            userId = userId, animalId = animalId, animal = animalRequest)

        val serverCommunicator = ServerCommunicator ()
        serverCommunicator.post("seen", SeenReportRequestDTO::class.java, seenReportDto) { responseBody ->
            if(responseBody != null){
                val objectMapper = ObjectMapper()
                val seenReportResponse = objectMapper.readValue(responseBody, SeenReportResponseDTO::class.java)

                val animalController = AnimalController()
                animalController.postAnimalPicture(seenReportResponse.animal.id, animalPhoto)
            }
        }
    }

}