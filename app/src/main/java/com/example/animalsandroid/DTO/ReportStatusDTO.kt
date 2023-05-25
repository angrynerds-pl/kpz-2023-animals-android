package com.example.animalsandroid.DTO

import com.fasterxml.jackson.annotation.JsonProperty

data class ReportStatusDTO(
    @JsonProperty
    val id : Int,
    @JsonProperty
    val name : String
)
