package com.example.animalsandroid.DTO.RequestDTO

import com.example.animalsandroid.DTO.AnimalSex
import com.fasterxml.jackson.annotation.JsonProperty

data class AnimalRequestDTO(
    @JsonProperty
    val name : String,
    @JsonProperty
    val chip : String,
    @JsonProperty
    val sex : AnimalSex,
    @JsonProperty
    val ownerId : Int,
    @JsonProperty
    val animalColorId : Int,
    @JsonProperty
    val breedId : Int
) {
    constructor() : this( "","", AnimalSex.NIEZNANA, 0, 0 ,0)
}
