package com.example.animalsandroid.DTO

import com.fasterxml.jackson.annotation.JsonProperty

data class BreedDTO(
    @JsonProperty
    val id : Int,
    @JsonProperty
    val name : String,
    @JsonProperty
    val type : TypeDTO
)
