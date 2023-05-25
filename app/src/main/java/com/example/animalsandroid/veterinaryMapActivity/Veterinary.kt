package com.example.animalsandroid.veterinaryMapActivity

import com.google.android.gms.maps.model.LatLng

class Veterinary {
    private val name: String
    private val geoPosition: LatLng

    constructor(name: String, geoPosition: LatLng) {
        this.name = name
        this.geoPosition = geoPosition
    }

    fun getName(): String{
        return name
    }

    fun getGeoPosition(): LatLng{
        return geoPosition
    }

}