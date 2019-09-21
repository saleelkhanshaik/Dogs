package com.example.saleel.dogs.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.saleel.dogs.ApplicationLevel.getProgressDrawable
import com.example.saleel.dogs.ApplicationLevel.loadImageView
import com.example.saleel.dogs.R
import com.example.saleel.dogs.databinding.ItemLayoutBinding
import com.example.saleel.dogs.model.DogBread
import com.example.saleel.dogs.view.fragments.DogListMainDirections
import kotlinx.android.synthetic.main.item_layout.view.*

class DogsListAdapter(val dogsList:ArrayList<DogBread>):RecyclerView.Adapter<DogsListAdapter.DogsViewHolder>(),DogClickListener{
    override fun onDogClicked(view: View) {

        val action = DogListMainDirections.actionFromlistToDetails(view.dogUUID.text.toString().toInt())
        Navigation.findNavController(view).navigate(action)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DogsViewHolder {
    val inflater=LayoutInflater.from(parent.context)
//        val view = inflater.inflate(R.layout.item_layout,parent,false)
        //we are passing the DaaBindingUtil ivew to adapter view
        val view = DataBindingUtil.inflate<ItemLayoutBinding>(inflater,R.layout.item_layout,parent,false)
        return DogsViewHolder(view)
    }

    override fun getItemCount()=dogsList.size

    override fun onBindViewHolder(holder: DogsViewHolder, position: Int) {

        holder.view.dog = dogsList[position]
        holder.view.dogclickevent = this
//        holder.view.dogName.text = dogsList[position].dogBread
//        holder.view.lifespam.text = dogsList[position].lifeSpam
//        holder.view.setOnClickListener {
//            //we are getting the ID based on the click event of the list and we are passing the
//            //uuid to detailsDog fragment
//
//            val action = DogListMainDirections.actionFromlistToDetails(dogsList[position].uuid)
//
//            Navigation.findNavController(it).navigate(action)
//        }
//        var imageURl = dogsList[position].dogImageURL
//        //Glide.with()
//        holder.view.dogImage.loadImageView(dogsList[position].dogImageURL, getProgressDrawable(holder.view.dogImage.context))
    }

    class DogsViewHolder(val view:ItemLayoutBinding):RecyclerView.ViewHolder(view.root)
    fun updateDogList(newDogList:List<DogBread>){
        dogsList.clear()
        dogsList.addAll(newDogList)
        notifyDataSetChanged()
    }

}