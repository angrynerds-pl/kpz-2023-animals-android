package com.example.animalsandroid.DTO

import com.fasterxml.jackson.annotation.JsonProperty

data class AnimalColorDTO(
    @JsonProperty
    val id : Int,
    @JsonProperty
    val name : String
)
