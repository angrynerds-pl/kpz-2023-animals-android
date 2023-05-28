package com.example.animalsandroid.veterinaryMapActivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.example.animalsandroid.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions

class VeterinaryMapActivity : AppCompatActivity() {

    private lateinit var mapFragment: SupportMapFragment
    private lateinit var googleMap: GoogleMap
    private var veterinariesList = mutableListOf<Veterinary>()

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
        var vet1 = Veterinary("Weterynarz Idio", "Władysława Nehringa 16, 50-381 Wrocław", "+48567774298", LatLng(51.111873484053355, 17.055949266367815))
        var vet2 = Veterinary("Nice vet", "Aleja Juliusza Słowackiego, 50-411 Wrocław", "+48665398446", LatLng(51.10886927594529, 17.043202322850778))
        var vet3 = Veterinary("DogWet", "Tadeusza Kościuszki 112, 50-441 Wrocław", "+48776123334", LatLng(51.10019279993451, 17.04426682457094))

        veterinariesList.add(vet1)
        veterinariesList.add(vet2)
        veterinariesList.add(vet3)
    }


    private fun showVeterinaries() {
        addMarkers()
        setMarkerClickListener()
        setInfoWindowAdapter()
    }

    private fun addMarkers() {
        for (vetPosition in veterinariesList) {
            val markerOptions = MarkerOptions()
                .position(vetPosition.getGeoPosition())
                .title(vetPosition.getName())
            val marker = googleMap.addMarker(markerOptions)
            marker?.tag = vetPosition // Przypisanie obiektu Veterinary jako tag markera
        }
    }

    private fun setMarkerClickListener() {
        googleMap.setOnMarkerClickListener { clickedMarker ->
            val clickedVeterinary = clickedMarker.tag as? Veterinary
            if (clickedVeterinary != null) {
                googleMap.animateCamera(
                    CameraUpdateFactory.newLatLngZoom(
                        clickedMarker.position,
                        CAMERA_ZOOM_LEVEL
                    )
                )
                clickedMarker.showInfoWindow()
            }
            true
        }
    }

    private fun setInfoWindowAdapter() {
        googleMap.setInfoWindowAdapter(object : GoogleMap.InfoWindowAdapter {
            override fun getInfoWindow(marker: Marker): View? {
                return null
            }

            override fun getInfoContents(marker: Marker): View? {
                val customInfoWindow = LayoutInflater.from(this@VeterinaryMapActivity).inflate(R.layout.custom_marker_info_window, null)
                val titleTextView = customInfoWindow.findViewById<TextView>(R.id.titleTextView)
                val addressTextView = customInfoWindow.findViewById<TextView>(R.id.addressTextView)
                val phoneTextView = customInfoWindow.findViewById<TextView>(R.id.phoneTextView)

                val veterinary = marker.tag as? Veterinary
                if (veterinary != null) {
                    titleTextView.text = veterinary.getName()
                    addressTextView.text = veterinary.getAddress()
                    phoneTextView.text = veterinary.getPhone()
                }

                return customInfoWindow
            }
        })
    }


    companion object {
        private const val CAMERA_ZOOM_LEVEL = 15f
    }
}
