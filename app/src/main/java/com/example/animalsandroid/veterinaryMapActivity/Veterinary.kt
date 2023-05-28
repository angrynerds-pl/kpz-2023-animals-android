package com.example.animalsandroid.veterinaryMapActivity

import com.google.android.gms.maps.model.LatLng

class Veterinary {
    private val name: String
    private val address : String
    private val phone : String
    private val geoPosition: LatLng

    constructor(name: String, address : String, phone : String, geoPosition: LatLng) {
        this.name = name
        this.address = address
        this.phone = phone
        this.geoPosition = geoPosition
    }

    fun getName(): String{
        return name
    }

    fun getAddress(): String{
        return address
    }

    fun getPhone(): String{
        return phone
    }

    fun getGeoPosition(): LatLng{
        return geoPosition
    }

}