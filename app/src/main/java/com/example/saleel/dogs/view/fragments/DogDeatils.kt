package com.example.saleel.dogs.view.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.example.saleel.dogs.ApplicationLevel.getProgressDrawable
import com.example.saleel.dogs.ApplicationLevel.loadImageView

import com.example.saleel.dogs.R
import com.example.saleel.dogs.viewmodel.DetailsViewModel
import kotlinx.android.synthetic.main.fragment_dog_deatils.*
import kotlinx.android.synthetic.main.fragment_dog_list_main.*


class DogDeatils : Fragment() {
    private lateinit var viewModel:DetailsViewModel
    val args : DogDeatilsArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dog_deatils, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(DetailsViewModel::class.java)
       // viewModel.fetch()
        val uuid = args.doguuid
        if(uuid != null ){
            viewModel.fetch(uuid)
        }
        obserViewModel()
    }
    fun obserViewModel(){
        viewModel.dogsList.observe(this, Observer {
            dog-> dog?.let {
            dogName.text = dog.dogBread
            dogpurpose.text = dog.breadFor
            dogtemparment.text = dog.temparment
            doglifespam.text = dog.lifeSpam
            dogImageView.loadImageView(dog.dogImageURL, getProgressDrawable(dogImageView.context))
        }
        })
    }
}
