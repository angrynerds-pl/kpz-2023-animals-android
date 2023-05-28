package com.example.animalsandroid

import android.graphics.Bitmap
import java.io.Serializable

class LostAnimal (
    var titleImage : Bitmap,
    var heading : String,
    var lostDate : String
) : Serializable