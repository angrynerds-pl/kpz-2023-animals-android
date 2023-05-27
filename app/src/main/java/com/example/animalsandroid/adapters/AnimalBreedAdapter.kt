package com.example.animalsandroid.adapters

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.animalsandroid.DTO.BreedDTO

class AnimalBreedAdapter(context: Context, resource: Int, objects: List<BreedDTO>) :
    ArrayAdapter<BreedDTO>(context, resource, objects) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = super.getView(position, convertView, parent)
        val color = getItem(position)
        val textView = view.findViewById<TextView>(android.R.id.text1)
        textView.text = color?.name
        return view
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = super.getDropDownView(position, convertView, parent)
        val color = getItem(position)
        val textView = view.findViewById<TextView>(android.R.id.text1)
        textView.text = color?.name
        return view
    }
}