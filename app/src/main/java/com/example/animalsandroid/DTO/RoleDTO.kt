package com.example.animalsandroid.DTO

import com.fasterxml.jackson.annotation.JsonProperty

data class RoleDTO(
    @JsonProperty
    val id : Int,
    @JsonProperty
    val name : String
) {
    constructor() : this(0, "")
}
