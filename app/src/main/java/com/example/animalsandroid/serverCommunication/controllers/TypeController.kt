package com.example.animalsandroid.serverCommunication.controllers

import com.example.animalsandroid.DTO.TypeDTO
import com.example.animalsandroid.serverCommunication.ServerCommunicator

class TypeController {

    fun getAllTypes(): List<TypeDTO> {
        val serverCommunicator = ServerCommunicator()
        return serverCommunicator.getAll("types", TypeDTO::class.java)
    }

    //postType do usuniecia ze wgledu na bezpieczenstwo
    fun postType(name : String){
        val typeDTO = TypeDTO(0, name = name)

        val serverCommunicator = ServerCommunicator ()
        serverCommunicator.post("types", TypeDTO::class.java, typeDTO)
    }
}