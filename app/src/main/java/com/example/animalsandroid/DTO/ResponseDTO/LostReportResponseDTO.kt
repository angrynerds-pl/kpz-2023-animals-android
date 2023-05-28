package com.example.animalsandroid.DTO.ResponseDTO

import com.example.animalsandroid.DTO.CoordinateDTO
import com.example.animalsandroid.DTO.ReportStatusDTO
import com.fasterxml.jackson.annotation.JsonProperty

data class LostReportResponseDTO(
    @JsonProperty
    val id  : Int,
    @JsonProperty
    val animal : AnimalResponseDTO,
    @JsonProperty
    val status : ReportStatusDTO,
    @JsonProperty
    val lostDate : String,
    @JsonProperty
    val description : String,
    @JsonProperty
    val coordinateDTO: CoordinateDTO
) {
    constructor() : this(0, AnimalResponseDTO(), ReportStatusDTO(), "", "", CoordinateDTO())
}
