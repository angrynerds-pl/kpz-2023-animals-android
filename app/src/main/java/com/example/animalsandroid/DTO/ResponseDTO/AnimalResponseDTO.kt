package com.example.animalsandroid.DTO.ResponseDTO
import com.example.animalsandroid.DTO.AnimalColorDTO
import com.example.animalsandroid.DTO.AnimalPictureDTO
import com.example.animalsandroid.DTO.AnimalSex
import com.example.animalsandroid.DTO.BreedDTO
import com.fasterxml.jackson.annotation.JsonProperty

data class AnimalResponseDTO(
    @JsonProperty
    val id : Int,
    @JsonProperty
    val chip : String,
    @JsonProperty
    val name : String,
    @JsonProperty
    val breed : BreedDTO,
    @JsonProperty
    val color : AnimalColorDTO,
    @JsonProperty
    val sex : AnimalSex,
    @JsonProperty
    val ownerId : Int,
    @JsonProperty
    val ownerName : String,
    @JsonProperty
    val ownerLastName : String,
    @JsonProperty
    val ownerPhoneNumber : String,
    @JsonProperty
    val animalPicture : AnimalPictureDTO

)
