package com.example.animalsandroid.DTO

import com.fasterxml.jackson.annotation.JsonProperty
import okhttp3.MediaType

data class AnimalPictureDTO(
    @JsonProperty
    val id : Int,
    @JsonProperty
    val contentType : String,
    @JsonProperty
    val url : String
) {
    constructor() : this(0, "","")

    constructor(contentType: String, url: String) : this(0, contentType,  url)
}
