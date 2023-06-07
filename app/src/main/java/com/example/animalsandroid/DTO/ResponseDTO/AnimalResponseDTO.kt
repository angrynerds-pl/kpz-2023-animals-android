package com.example.animalsandroid.DTO.ResponseDTO
import android.annotation.SuppressLint
import androidx.annotation.Nullable
import com.example.animalsandroid.DTO.*
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class AnimalResponseDTO (
    @JsonProperty
    val id : Int,
    @JsonProperty
    val chip : String?,
    @JsonProperty
    val name : String?,
    @JsonProperty
    val breed : BreedDTO,
    @JsonProperty
    val color : AnimalColorDTO,
    @JsonProperty
    val sex : AnimalSex,
    @JsonProperty
    val ownerId : Int,
    @JsonProperty
    val ownerName : String?,
    @JsonProperty
    val ownerLastName : String?,
    @JsonProperty
    val ownerPhoneNumber : String?,
    @JsonProperty
    val animalPicture: List<AnimalPictureDTO>

){
    constructor() : this(0,"", "", BreedDTO(), AnimalColorDTO(), AnimalSex.NIEZNANA, 0,
        "", "", "", listOf(AnimalPictureDTO()))
}
