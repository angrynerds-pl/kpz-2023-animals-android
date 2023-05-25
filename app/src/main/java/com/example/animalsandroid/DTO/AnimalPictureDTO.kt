package com.example.animalsandroid.DTO

import com.fasterxml.jackson.annotation.JsonProperty

data class AnimalPictureDTO(
    @JsonProperty
    val id : Int,
    @JsonProperty
    val contentType : String,
    @JsonProperty
    val url : String
)
