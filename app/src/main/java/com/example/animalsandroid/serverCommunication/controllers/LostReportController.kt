package com.example.animalsandroid.serverCommunication.controllers

import com.example.animalsandroid.DTO.CoordinateDTO
import com.example.animalsandroid.DTO.RequestDTO.LostReportRequestDTO
import com.example.animalsandroid.DTO.ResponseDTO.LostReportResponseDTO
import com.example.animalsandroid.serverCommunication.ServerCommunicator

class LostReportController {

    fun getAllLostReports(): List<LostReportResponseDTO> {
        val serverCommunicator = ServerCommunicator()
        return serverCommunicator.getAll("lost", LostReportResponseDTO::class.java)
    }

    fun postLostReport(lostDate : String, coordinate: CoordinateDTO, description : String, animalId : Int, reportStatusId : Int){
        val lostReportDto = LostReportRequestDTO(lostDate = lostDate, coordinate = coordinate, description = description,
            animalId = animalId, reportStatusId = reportStatusId)

        val serverCommunicator = ServerCommunicator ()
        serverCommunicator.post("lost", LostReportRequestDTO::class.java, lostReportDto)
    }

}