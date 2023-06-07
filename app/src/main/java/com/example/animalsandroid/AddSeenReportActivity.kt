package com.example.animalsandroid

import android.Manifest
import android.app.DatePickerDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.animalsandroid.DTO.*
import com.example.animalsandroid.DTO.RequestDTO.AnimalRequestDTO
import com.example.animalsandroid.DTO.RequestDTO.SeenReportRequestDTO
import com.example.animalsandroid.DTO.ResponseDTO.SeenReportResponseDTO
import com.example.animalsandroid.adapters.AnimalBreedAdapter
import com.example.animalsandroid.adapters.AnimalColorAdapter
import com.example.animalsandroid.adapters.AnimalTypeAdapter
import com.example.animalsandroid.databinding.ActivityAddSeenReportBinding
import com.example.animalsandroid.serverCommunication.controllers.AnimalColorController
import com.example.animalsandroid.serverCommunication.controllers.AnimalController
import com.example.animalsandroid.serverCommunication.controllers.SeenReportController
import com.example.animalsandroid.serverCommunication.controllers.TypeController
import com.fasterxml.jackson.databind.ObjectMapper
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.model.MarkerOptions
import java.io.ByteArrayOutputStream
import java.text.SimpleDateFormat
import java.util.*


class AddSeenReportActivity : AppCompatActivity() {

    private lateinit var pickedBitMap: Bitmap
    private lateinit var bilding: ActivityAddSeenReportBinding
    private lateinit var googleMap : GoogleMap
    private var dialogMapView : MapView? = null

    private lateinit var selectedBreed : BreedDTO
    private lateinit var selectedType : TypeDTO
    private lateinit var selectedColor : AnimalColorDTO
    private lateinit var selectedSex : AnimalSex
    private lateinit var selectedCoordinate: CoordinateDTO
    private lateinit var selectedDate : String
    private lateinit var description : String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bilding = ActivityAddSeenReportBinding.inflate(layoutInflater)
        setContentView(bilding.root)
        //setContentView(R.layout.activity_add_found_report)

        //------spinners------
        animalTypeSpinner()
        animalColorSpinner()
        animalSexSpinner()

        //-------map-------
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
        if(!::pickedBitMap.isInitialized || !::selectedBreed.isInitialized || !::selectedType.isInitialized ||
            !::selectedSex.isInitialized || !::selectedCoordinate.isInitialized || !::selectedDate.isInitialized
            || description == "") {
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

        //userId jest hardcodowane
        val seenReportController = SeenReportController()
        val animal = AnimalRequestDTO(sex = selectedSex, animalColorId = selectedColor.id, breedId = selectedBreed.id)
        seenReportController.postSeenReport(lostDate = selectedDate, coordinate = selectedCoordinate, description = description,
            userId = 1, null, animalRequest = animal, animalPhoto = compressBitmap(pickedBitMap))



        val intent = Intent()
        //intent.putExtra("EXTRA_STRING", "x")
        intent.putExtra("EXTRA_BOOLEAN", true)
        //intent.putExtra("EXTRA_DESC", description)
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
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 1)
        }else{
            val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(galleryIntent, 2)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode == 2 && resultCode == RESULT_OK && data != null ){
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
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10f))
            dialogMapView?.onResume()

            val checkBox = findViewById<CheckBox>(R.id.localityCheckBox)
            checkBox.isChecked = true
        }
    }


    //-------------------------Spinners-------------------------
    private fun animalColorSpinner(){
        val animalColorController = AnimalColorController()
        val animalColors = animalColorController.getAllAnimalColors()
        val spinner: Spinner = findViewById(R.id.animalColorSpinner)

        val adapter = AnimalColorAdapter(this, android.R.layout.simple_spinner_item, animalColors)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        spinner.adapter = adapter

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                selectedColor = parent.getItemAtPosition(position) as AnimalColorDTO
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
            }
        }
    }

    private fun animalBreedSpinner(){
        val typeController = TypeController()
        val animalBreeds = typeController.getBreedsForType(selectedType.id)

        val spinner: Spinner = findViewById(R.id.breedSpinner)

        val adapter = AnimalBreedAdapter(this, android.R.layout.simple_spinner_item, animalBreeds)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        spinner.adapter = adapter

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                selectedBreed = parent.getItemAtPosition(position) as BreedDTO
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
            }
        }
    }

    private fun animalTypeSpinner(){
        val typeController = TypeController()
        val animalTypes = typeController.getAllTypes()
        val spinner: Spinner = findViewById(R.id.typeSpinner)

        val adapter = AnimalTypeAdapter(this, android.R.layout.simple_spinner_item, animalTypes)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        spinner.adapter = adapter

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                selectedType = parent.getItemAtPosition(position) as TypeDTO
                animalBreedSpinner()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
            }
        }
    }

    private fun animalSexSpinner() {
        val animalSex = AnimalSex.values()
        val spinner: Spinner = findViewById(R.id.animalSexSpinner)

        val adapter = ArrayAdapter<AnimalSex>(this, android.R.layout.simple_spinner_item, animalSex)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        spinner.adapter = adapter

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                selectedSex = parent.getItemAtPosition(position) as AnimalSex
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