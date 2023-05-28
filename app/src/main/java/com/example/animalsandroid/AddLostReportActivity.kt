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
import com.example.animalsandroid.databinding.ActivityAddLostReportBinding
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import java.io.ByteArrayOutputStream
import java.text.SimpleDateFormat
import androidx.appcompat.app.AlertDialog
import com.example.animalsandroid.DTO.*
import com.example.animalsandroid.DTO.RequestDTO.AnimalRequestDTO
import com.example.animalsandroid.DTO.ResponseDTO.AnimalResponseDTO
import com.example.animalsandroid.adapters.AnimalAdapter
import com.example.animalsandroid.adapters.AnimalBreedAdapter
import com.example.animalsandroid.adapters.AnimalColorAdapter
import com.example.animalsandroid.adapters.AnimalTypeAdapter
import com.example.animalsandroid.serverCommunication.controllers.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.MarkerOptions
import java.util.*

class AddLostReportActivity : AppCompatActivity(){

    private lateinit var pickedBitMap: Bitmap
    private lateinit var bilding: ActivityAddLostReportBinding
    private lateinit var googleMap : GoogleMap
    private var dialogMapView : MapView? = null

    private lateinit var selectedAnimal : AnimalResponseDTO
    private lateinit var selectedCoordinate: CoordinateDTO
    private lateinit var selectedDate : String
    private lateinit var description : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bilding = ActivityAddLostReportBinding.inflate(layoutInflater)
        setContentView(bilding.root)
        //setContentView(R.layout.activity_add_missing_report)

        //------spinners------
        animalSpinner()

        //------map------
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
        if(!::pickedBitMap.isInitialized || !::selectedAnimal.isInitialized || !::selectedCoordinate.isInitialized
            || !::selectedDate.isInitialized || description == "") {
            toastMsg("Nie podano wszystkich danych")
        }
        else {
            toastMsg("Zatwierdzono")
            sendData()
        }
    }

    private fun getData() {
        description = findViewById<EditText>(R.id.editTextDesc).text.toString()
    }

    private fun sendData(){

        val lostReportController = LostReportController()
        lostReportController.postLostReport(lostDate = selectedDate, coordinate = selectedCoordinate, description = description,
            animalId = selectedAnimal.id, reportStatusId = 1)

        //userId jest hardcodowane + trzeba jeszcze dodać wysyłanie zdjęcia

        val intent = Intent()
        intent.putExtra("EXTRA_STRING", "x")
        intent.putExtra("EXTRA_BOOLEAN", true)
        intent.putExtra("EXTRA_DESC", description)
        intent.putExtra("EXTRA_JPEG", compressBitmap(pickedBitMap))
        setResult(1, intent)
        finish()
    }

    //---------------------------BitMap---------------------------
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

    //---------------------------Date---------------------------
    fun pickDate(view: View){
        var formatDate = SimpleDateFormat( "yyyy-MM-dd", Locale.ROOT)
        val getDate = Calendar.getInstance()

        val datePicker = DatePickerDialog(this, android.R.style.Theme_Holo_Light_Dialog_MinWidth,
            DatePickerDialog.OnDateSetListener { datePicker, i, i2, i3 ->
                val selectDate = Calendar.getInstance()
                selectDate.set(Calendar.YEAR, i)
                selectDate.set(Calendar.MONTH, i2)
                selectDate.set(Calendar.DAY_OF_MONTH, i3)

                selectedDate = formatDate.format(selectDate.time)
                val dayTextView = findViewById<TextView>(R.id.dateTextView)
                dayTextView.text = selectedDate
            }, getDate.get(Calendar.YEAR), getDate.get(Calendar.MONTH), getDate.get(Calendar.DAY_OF_MONTH))
        datePicker.show()
    }


    //----------------------------Map----------------------------
    private fun showMapDialog() {
        val dialogView = layoutInflater.inflate(R.layout.dialog_map, null)
        val dialogBuilder = createDialogBuilder().setView(dialogView)
        val dialog = dialogBuilder.create()
        dialog.show()

        dialogMapView = dialogView.findViewById<MapView>(R.id.dialogMapView)
        dialogMapView?.onCreate(dialog.onSaveInstanceState())
        dialogMapView?.onResume()
        dialogMapView?.getMapAsync { map ->
            googleMap = map
            setupMap()
        }
    }

    private fun createDialogBuilder(): AlertDialog.Builder {
        return AlertDialog.Builder(this)
            .setTitle("Wybierz punkt")
            .setPositiveButton("OK") { dialog, _ ->
                if (::selectedCoordinate.isInitialized) {
                    dialog.dismiss()
                } else {
                    Toast.makeText(this, "Wybierz punkt na mapie", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Anuluj") { dialog, _ ->
                dialog.dismiss()
            }
    }

    private fun setupMap() {
        googleMap.setOnMapClickListener { latLng ->
            googleMap.clear()
            selectedCoordinate = CoordinateDTO(latLng.latitude, latLng.longitude)

            googleMap.addMarker(MarkerOptions().position(latLng))
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f))
            dialogMapView?.onResume()

            val checkBox = findViewById<CheckBox>(R.id.localityCheckBox)
            checkBox.isChecked = true
        }
    }


    //---------------------------Spinner---------------------------
    private fun animalSpinner(){
        val userController = UserController()
        val userAnimals = userController.getUserAnimals(1)
        val spinner: Spinner = findViewById(R.id.animalSpinner)

        val adapter = AnimalAdapter(this, android.R.layout.simple_spinner_item, userAnimals)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        spinner.adapter = adapter

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                selectedAnimal = parent.getItemAtPosition(position) as AnimalResponseDTO
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
            }
        }
    }


    override fun onResume() {
        super.onResume()
        dialogMapView?.onResume()
    }

    override fun onPause() {
        super.onPause()
        dialogMapView?.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        dialogMapView?.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        dialogMapView?.onLowMemory()
    }

}