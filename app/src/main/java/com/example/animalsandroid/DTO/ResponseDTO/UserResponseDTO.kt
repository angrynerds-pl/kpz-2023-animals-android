package com.example.animalsandroid.DTO.ResponseDTO

import com.example.animalsandroid.DTO.AnimalColorDTO
import com.example.animalsandroid.DTO.AnimalPictureDTO
import com.example.animalsandroid.DTO.AnimalSex
import com.example.animalsandroid.DTO.BreedDTO
import com.example.animalsandroid.serverCommunication.ServerCommunicator
import com.fasterxml.jackson.annotation.JsonProperty

data class UserResponseDTO(
    @JsonProperty
    val id : Int,
    @JsonProperty
    val name : String,
    @JsonProperty
    val lastName : String,
    @JsonProperty
    val phoneNumber : String,
    @JsonProperty
    val email : String,
    @JsonProperty
    val animals : Array<AnimalResponseDTO>
){
    constructor() : this(0,"", "", "", "", arrayOf(AnimalResponseDTO())
    )

    fun getUserAnimals(): List<AnimalResponseDTO> {
        return animals.toMutableList()
    }
}
