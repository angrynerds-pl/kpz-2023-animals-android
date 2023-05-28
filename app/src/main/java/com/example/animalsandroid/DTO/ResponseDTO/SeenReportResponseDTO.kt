package com.example.animalsandroid.DTO.ResponseDTO

import com.example.animalsandroid.DTO.CoordinateDTO
import com.example.animalsandroid.DTO.ReportStatusDTO
import com.fasterxml.jackson.annotation.JsonProperty

data class SeenReportResponseDTO(
    @JsonProperty
    val id : Int,
    @JsonProperty
    val userId : Int,
    @JsonProperty
    val userName : String,
    @JsonProperty
    val userLastName : String,
    @JsonProperty
    val userPhoneNumber : String,
    @JsonProperty
    val animal : AnimalResponseDTO,
    @JsonProperty
    val coordinateDTO: CoordinateDTO,
    @JsonProperty
    val seenDate : String,
    @JsonProperty
    val description : String
){
    constructor() : this(0, 0, "", "", "", AnimalResponseDTO(),
        CoordinateDTO(), "", "")
}
