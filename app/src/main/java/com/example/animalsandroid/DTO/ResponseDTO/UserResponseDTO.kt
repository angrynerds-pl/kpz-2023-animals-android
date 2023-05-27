package com.example.animalsandroid.DTO.ResponseDTO

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
)
