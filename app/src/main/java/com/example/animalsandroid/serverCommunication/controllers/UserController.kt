package com.example.animalsandroid.serverCommunication.controllers

import com.example.animalsandroid.DTO.ResponseDTO.AnimalResponseDTO
import com.example.animalsandroid.DTO.ResponseDTO.UserResponseDTO
import com.example.animalsandroid.serverCommunication.ServerCommunicator
import com.fasterxml.jackson.databind.ObjectMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserController {


    fun getUserAnimals(userId: Int): List<AnimalResponseDTO> {
        val serverCommunicator = ServerCommunicator()
        return serverCommunicator.get("users/$userId", UserResponseDTO::class.java).getUserAnimals()
    }


}