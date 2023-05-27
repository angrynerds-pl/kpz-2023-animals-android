package com.example.animalsandroid.DTO

import com.fasterxml.jackson.annotation.JsonProperty

data class CoordinateDTO(
    @JsonProperty
    val x : Double,
    @JsonProperty
    val y : Double
) {
    constructor() : this(0.0, 0.0)
}
