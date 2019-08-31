package com.example.saleel.dogs.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.saleel.dogs.model.DogBread

class DetailsViewModel : ViewModel() {
    //Base API =https://raw.githubusercontent.com/DevTides/DogsApi/master/dogs.json
    val dogsList = MutableLiveData<DogBread> ()
    fun fetch(){
        val dog1 = DogBread("1000","saleel","10","A","BeadFor1","40","")
        dogsList.value = dog1
    }
}