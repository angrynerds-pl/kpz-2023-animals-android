package com.example.animalsandroid

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.*
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.animalsandroid.databinding.ActivityAddMissingReportBinding
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import java.io.ByteArrayOutputStream
import java.text.SimpleDateFormat
import androidx.appcompat.app.AlertDialog
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import java.util.*

class AddMissingReportActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var name : String
    private lateinit var locality : String
    private lateinit var date : String
    private lateinit var concatText : String
    private lateinit var description : String

    private lateinit var pickedBitMap: Bitmap
    private lateinit var bilding: ActivityAddMissingReportBinding
    private lateinit var spinner: Spinner
    private lateinit var mapView : MapView
    private lateinit var googleMap : GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bilding = ActivityAddMissingReportBinding.inflate(layoutInflater)
        setContentView(bilding.root)
        //setContentView(R.layout.activity_add_missing_report)

        //------spinner------
        spinner = findViewById(R.id.spinner)
        val options = arrayOf("Option 1", "Option 2", "Option 3")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, options)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

        //------mapView------
        mapView = findViewById(R.id.mapView)
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this)

        val buttonPickLocation = findViewById<Button>(R.id.buttonPickLocation)
        buttonPickLocation.setOnClickListener{
            showMapDialog()
        }
    }


    private fun toastMsg(msg:String){
        val toast = Toast.makeText(this,msg,Toast.LENGTH_LONG)
        toast.show()
    }

    fun displayToastMsg(v: View){

        getData()
        if(name == "" || locality == "" || date == "" || !::pickedBitMap.isInitialized) {
            toastMsg("Nie podano wszystkich danych")
        }
        else {
            toastMsg("Zatwierdzono")
            sendData()
        }
    }

    private fun getData() {
        name = findViewById<EditText>(R.id.editTextSpecies).text.toString()
        locality = findViewById<EditText>(R.id.editTextLocality).text.toString()
        date = findViewById<EditText>(R.id.editTextDate).text.toString()
        concatText = name.plus("\n").plus(locality).plus("\n").plus(date)
        description = findViewById<EditText>(R.id.editTextDesc).text.toString()
    }

    private fun sendData(){
        val intent = Intent()
        intent.putExtra("EXTRA_STRING", concatText)
        intent.putExtra("EXTRA_BOOLEAN", true)
        intent.putExtra("EXTRA_DESC", description)
        intent.putExtra("EXTRA_JPEG", compressBitmap(pickedBitMap))
        setResult(1, intent)
        finish()
    }

    private fun compressBitmap(bitmap: Bitmap): ByteArray {
        val outputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, outputStream)
        return outputStream.toByteArray()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(galleryIntent, 2)
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    fun pickedPhoto (view: View){
        if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE), 1)
        }else{
            val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(galleryIntent, 2)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode == 2 && resultCode == Activity.RESULT_OK && data != null ){
            var pickedPhoto: Uri = data.data!!
            pickedBitMap = if (Build.VERSION.SDK_INT >= 28){
                ImageDecoder.decodeBitmap(ImageDecoder.createSource(this.contentResolver, pickedPhoto!!))
            }else{
                MediaStore.Images.Media.getBitmap(this.contentResolver, pickedPhoto)
            }
            bilding.imageView.setImageBitmap(pickedBitMap)
            bilding.textViewAddPhoto.text = ""
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    fun pickDate(view: View){
        var formatDate = SimpleDateFormat( "dd MMMM YYYY", Locale.ROOT)
        val getDate = Calendar.getInstance()

        val datePicker = DatePickerDialog(this, android.R.style.Theme_Holo_Light_Dialog_MinWidth,
            DatePickerDialog.OnDateSetListener { datePicker, i, i2, i3 ->
                val selectDate = Calendar.getInstance()
                selectDate.set(Calendar.YEAR, i)
                selectDate.set(Calendar.MONTH, i2)
                selectDate.set(Calendar.DAY_OF_MONTH, i3)
                val date = formatDate.format(selectDate.time)
                findViewById<EditText>(R.id.editTextDate).setText(date)
            }, getDate.get(Calendar.YEAR), getDate.get(Calendar.MONTH), getDate.get(Calendar.DAY_OF_MONTH))
        datePicker.show()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        try {
            outState?.putByteArray("IMAGE", compressBitmap(pickedBitMap))
        }catch(ignored : UninitializedPropertyAccessException){ }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        val savedByteArray = savedInstanceState?.getByteArray("IMAGE")
        if(savedByteArray != null && savedByteArray.isNotEmpty()){
            pickedBitMap = BitmapFactory.decodeByteArray(savedByteArray, 0, savedByteArray.size)
            bilding.imageView.setImageBitmap(pickedBitMap)
            bilding.textViewAddPhoto.text = ""}
    }

    override fun onMapReady(map: GoogleMap) {
        googleMap = map

        googleMap.setOnMapClickListener { latLng ->
            googleMap.addMarker(MarkerOptions().position(latLng))
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f))
        }
    }

    private fun showMapDialog() {
        val dialogView = layoutInflater.inflate(R.layout.dialog_map, null)
        val dialogBuilder = AlertDialog.Builder(this)
            .setTitle("Wybierz punkt")
            .setView(dialogView)
            .setPositiveButton("OK"){ dialog, _ ->
                dialog.dismiss()
            }
            .setNegativeButton("Anuluj") { dialog, _ ->
                dialog.dismiss()
            }
        val dialog = dialogBuilder.create();
        dialog.show()

        var dialogMapView = dialogView.findViewById<MapView>(R.id.dialogMapView)
        dialogMapView.onCreate(dialog.onSaveInstanceState())
        dialogMapView.onResume()
        dialogMapView.getMapAsync { map ->
            googleMap = map
            googleMap.setOnMapClickListener { latLng ->
                googleMap.addMarker(MarkerOptions().position(latLng))
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f))
            }
        }

    }
    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }

}