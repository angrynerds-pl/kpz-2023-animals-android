package com.example.animalsandroid.DTO.RequestDTO

import com.example.animalsandroid.DTO.CoordinateDTO
import com.fasterxml.jackson.annotation.JsonProperty

data class SeenReportRequestDTO(
    @JsonProperty
    val lastDate : String,
    @JsonProperty
    val coordinate: CoordinateDTO,
    @JsonProperty
    val description : String,
    @JsonProperty
    val userId : Int,
    @JsonProperty
    val animalId : Int,
    @JsonProperty
    val animalRequestDTO: AnimalRequestDTO
)
