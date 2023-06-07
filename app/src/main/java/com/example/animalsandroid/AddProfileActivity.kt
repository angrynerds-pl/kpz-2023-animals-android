package com.example.animalsandroid

import android.app.Activity
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
import com.example.animalsandroid.DTO.*
import com.example.animalsandroid.adapters.AnimalBreedAdapter
import com.example.animalsandroid.adapters.AnimalColorAdapter
import com.example.animalsandroid.adapters.AnimalTypeAdapter
import com.example.animalsandroid.databinding.ActivityAddProfileBinding
import com.example.animalsandroid.serverCommunication.controllers.AnimalColorController
import com.example.animalsandroid.serverCommunication.controllers.AnimalController
import com.example.animalsandroid.serverCommunication.controllers.TypeController
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import java.io.ByteArrayOutputStream

class AddProfileActivity : AppCompatActivity() {

    private lateinit var name : String
    private lateinit var chipNumber : String

    private lateinit var pickedBitMap: Bitmap
    private lateinit var bilding: ActivityAddProfileBinding
    private lateinit var googleMap : GoogleMap
    private var dialogMapView : MapView? = null

    private lateinit var selectedBreed : BreedDTO
    private lateinit var selectedType : TypeDTO
    private lateinit var selectedColor : AnimalColorDTO
    private lateinit var selectedSex : AnimalSex

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bilding = ActivityAddProfileBinding.inflate(layoutInflater)
        setContentView(bilding.root)

        //------spinners------
        animalTypeSpinner()
        animalColorSpinner()
        animalSexSpinner()
    }

    private fun toastMsg(msg:String){
        val toast = Toast.makeText(this,msg, Toast.LENGTH_LONG)
        toast.show()
    }

    fun displayToastMsg(v: View){

        getData()
        if(name == "" ||  !::pickedBitMap.isInitialized || !::selectedBreed.isInitialized || !::selectedType.isInitialized || !::selectedSex.isInitialized) {
            toastMsg("Nie podano wszystkich danych")
        }
        else {
            toastMsg("Zatwierdzono")
            addAnimal()
            finish()
        }
    }

    fun addAnimal(){
        val animalController = AnimalController()
        if(chipNumber == "") {
            animalController.postAnimal(name, null, selectedSex, 1,
                selectedColor.id, selectedBreed.id, compressBitmap(pickedBitMap))
        }else {
            animalController.postAnimal(name, chipNumber, selectedSex, 1,
                selectedColor.id, selectedBreed.id, compressBitmap(pickedBitMap))
        }
    }

    fun getData(){
        name = findViewById<EditText>(R.id.name).text.toString()
        chipNumber = findViewById<EditText>(R.id.chipNumber).text.toString()
    }

//    fun sendData(){  //chwilowo niepotrzebna ale zostawiam na wszelki wypadek
//        val intent = Intent()
//        intent.putExtra("EXTRA_BOOLEAN", true)
//        intent.putExtra("EXTRA_NAME", name)
//        intent.putExtra("EXTRA_CHIP", chipNumber)
//        intent.putExtra("EXTRA_JPEG", compressBitmap(pickedBitMap))
//        setResult(1, intent)
//        finish()
//    }

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

    private fun animalBreedSpinner(){
        val typeController = TypeController()
        val animalBreeds = typeController.getBreedsForType(selectedType.id)

        val spinner: Spinner = findViewById(R.id.spinnerBreed)

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
        val spinner: Spinner = findViewById(R.id.spinnerType)

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
        val spinner: Spinner = findViewById(R.id.spinnerSex)

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

    private fun animalColorSpinner(){
        val animalColorController = AnimalColorController()
        val animalColors = animalColorController.getAllAnimalColors()
        val spinner: Spinner = findViewById(R.id.spinnerColor)

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