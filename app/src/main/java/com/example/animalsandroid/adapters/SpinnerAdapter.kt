package com.example.animalsandroid.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class SpinnerAdapter<T>(context: Context, objects: List<T>) :
    ArrayAdapter<T>(context, android.R.layout.simple_spinner_item, objects) {

    private val inflater: LayoutInflater = LayoutInflater.from(context)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = super.getView(position, convertView, parent)
        val item = getItem(position)
        val textView = view.findViewById<TextView>(android.R.id.text1)
        textView.text = item.toString()
        return view
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = inflater.inflate(android.R.layout.simple_spinner_dropdown_item, parent, false)
        val item = getItem(position)
        val textView = view.findViewById<TextView>(android.R.id.text1)
        textView.text = item.toString()
        return view
    }

    override fun setDropDownViewResource(resource: Int) {
        super.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
    }

}

