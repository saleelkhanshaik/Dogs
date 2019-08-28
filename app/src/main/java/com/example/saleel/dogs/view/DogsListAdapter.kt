package com.example.saleel.dogs.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.saleel.dogs.R
import com.example.saleel.dogs.model.DogBread
import com.example.saleel.dogs.view.fragments.DogListMainDirections
import kotlinx.android.synthetic.main.item_layout.view.*

class DogsListAdapter(val dogsList:ArrayList<DogBread>):RecyclerView.Adapter<DogsListAdapter.DogsViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DogsViewHolder {
    val inflater=LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_layout,parent,false)
        return DogsViewHolder(view)
    }

    override fun getItemCount()=dogsList.size

    override fun onBindViewHolder(holder: DogsViewHolder, position: Int) {
        holder.view.dogName.text = dogsList[position].dogBread
        holder.view.lifespam.text = dogsList[position].lifeSpam
        holder.view.setOnClickListener {
            Navigation.findNavController(it).navigate(DogListMainDirections.actionFromlistToDetails())
        }
    }

    class DogsViewHolder(val view:View):RecyclerView.ViewHolder(view)
    fun updateDogList(newDogList:ArrayList<DogBread>){
        dogsList.clear()
        dogsList.addAll(newDogList)
        notifyDataSetChanged()
    }

}