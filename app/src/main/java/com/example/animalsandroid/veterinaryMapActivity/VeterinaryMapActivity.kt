package com.example.animalsandroid.veterinaryMapActivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.animalsandroid.R
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class VeterinaryMapActivity : AppCompatActivity() {

    private lateinit var mapFragment: SupportMapFragment
    private lateinit var googleMap: GoogleMap
    private var VeterinariesList = mutableListOf<Veterinary>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_veterinary_map)

        mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(OnMapReadyCallback {
            googleMap = it

            addVeterinaries()
            showVeterinaries()
        })
    }

    fun addVeterinaries(){
        var vet1 = Veterinary("Weterynarz_1", LatLng(51.111873484053355, 17.055949266367815))
        var vet2 = Veterinary("Weterynarz_2", LatLng(51.10886927594529, 17.043202322850778))
        var vet3 = Veterinary("Weterynarz_3", LatLng(51.10019279993451, 17.04426682457094))

        VeterinariesList.add(vet1)
        VeterinariesList.add(vet2)
        VeterinariesList.add(vet3)
    }

    fun showVeterinaries(){
        for(vetPosition in VeterinariesList){
            googleMap.addMarker(MarkerOptions().position(vetPosition.getGeoPosition()).title(vetPosition.getName()))
        }
    }
}
