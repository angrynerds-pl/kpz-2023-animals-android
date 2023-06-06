package com.example.animalsandroid

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.imageview.ShapeableImageView

class LostAnimalAdapter(private val animalsList : ArrayList<LostAnimal>) :
    RecyclerView.Adapter<LostAnimalAdapter.MyViewHolder>() {

    private lateinit var mListener : onItemClickListener

    interface onItemClickListener{

        fun onItemClick(position : Int)

    }

    fun setOnClickListener(listener : onItemClickListener){
        mListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.lost_animal_list_item,
            parent,false)
        return MyViewHolder(itemView, mListener)
    }

    override fun getItemCount(): Int {
        return animalsList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val currentItem = animalsList[position]
        holder.titleImage.setImageResource(currentItem.imageID) // holder.titleImage.setImageBitmap(currentItem.titleImage)
        holder.tvHeading.text = currentItem.heading
        holder.tvDate.text = currentItem.lostDate
    }


    class MyViewHolder(itemView : View, listener : onItemClickListener) : RecyclerView.ViewHolder(itemView){
        val titleImage  : ShapeableImageView = itemView.findViewById(R.id.title_image)
        val tvHeading : TextView = itemView.findViewById(R.id.tvHeading)
        val tvDate : TextView = itemView.findViewById(R.id.tvDate)

        init {
            itemView.setOnClickListener{
                listener.onItemClick(adapterPosition)
            }
        }

    }

}