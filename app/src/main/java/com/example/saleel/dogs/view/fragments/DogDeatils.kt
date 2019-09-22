package com.example.saleel.dogs.view.fragments


import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import androidx.palette.graphics.Palette
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.saleel.dogs.ApplicationLevel.getProgressDrawable
import com.example.saleel.dogs.ApplicationLevel.loadImageView

import com.example.saleel.dogs.R
import com.example.saleel.dogs.databinding.FragmentDogDeatilsBinding
import com.example.saleel.dogs.model.DogPallate
import com.example.saleel.dogs.viewmodel.DetailsViewModel
import kotlinx.android.synthetic.main.fragment_dog_deatils.view.*


class DogDeatils : Fragment() {
    private lateinit var viewModel:DetailsViewModel
    //for binding
    private lateinit var dataBinding : FragmentDogDeatilsBinding
    val args : DogDeatilsArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        dataBinding =DataBindingUtil.inflate(inflater,R.layout.fragment_dog_deatils,container,false)
       // return inflater.inflate(R.layout.fragment_dog_deatils, container, false)
        return dataBinding.root
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
            dogdata-> dogdata?.let {
            dataBinding.dogdetails = dogdata
            //dogdetails
//            dogName.text = dog.dogBread
//            dogpurpose.text = dog.breadFor
//            dogtemparment.text = dog.temparment
//            doglifespam.text = dog.lifeSpam
//            dogImageView.loadImageView(dog.dogImageURL, getProgressDrawable(dogImageView.context))

            it.dogImageURL?.let {
                setupBackground(it)
            }
        }
        })
    }

    private fun setupBackground(it: String) {
        Glide.with(this).asBitmap()
            .load(it).into(object :CustomTarget<Bitmap>(){
                override fun onLoadCleared(placeholder: Drawable?) {

                }

                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    Palette.from(resource).generate { palette ->
                        val intColor = palette?.vibrantSwatch?.rgb?: 0
                        val myPallete = DogPallate(intColor)
                        dataBinding.dogpallete = myPallete
                    }
                }
            })

    }
}
