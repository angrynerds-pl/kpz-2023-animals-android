package com.example.animalsandroid.DTO.RequestDTO

import com.example.animalsandroid.DTO.CoordinateDTO
import com.fasterxml.jackson.annotation.JsonProperty

data class LostReportRequestDTO(
    @JsonProperty
    val lostDate : String,
    @JsonProperty
    val coordinate: CoordinateDTO,
    @JsonProperty
    val description : String,
    @JsonProperty
    val animalId : Int,
    @JsonProperty
    val reportStatusId : Int
) {
    constructor(lostDate : String, coordinate: CoordinateDTO, description : String, animalId : Int) : this(lostDate, coordinate, description, animalId, 0)
}
