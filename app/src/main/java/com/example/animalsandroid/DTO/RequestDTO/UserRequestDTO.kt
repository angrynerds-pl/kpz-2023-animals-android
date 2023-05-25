package com.example.animalsandroid.DTO.RequestDTO
import com.fasterxml.jackson.annotation.JsonProperty

data class UserRequestDTO(
    @JsonProperty
    val name : String,
    @JsonProperty
    val lastName : String,
    @JsonProperty
    val phoneNumber : String,
    @JsonProperty
    val email : String,
    @JsonProperty
    val password : String,
    @JsonProperty
    val idRole : Int
)
