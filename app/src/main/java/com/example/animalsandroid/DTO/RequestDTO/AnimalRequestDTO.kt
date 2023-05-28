package com.example.animalsandroid.DTO.RequestDTO

import com.example.animalsandroid.DTO.AnimalSex
import com.fasterxml.jackson.annotation.JsonProperty

data class AnimalRequestDTO(
    @JsonProperty
    val name : String?,
    @JsonProperty
    val chip : String?,
    @JsonProperty
    val sex : AnimalSex,
    @JsonProperty
    val ownerId : Int?,
    @JsonProperty
    val animalColorId : Int,
    @JsonProperty
    val breedId : Int
) {
    constructor() : this( null, null, AnimalSex.NIEZNANA, null, 0, 0)
    constructor(animalColorId : Int, breedId : Int, sex : AnimalSex = AnimalSex.NIEZNANA) : this( null ,
        null, sex, null, animalColorId ,breedId)

}
