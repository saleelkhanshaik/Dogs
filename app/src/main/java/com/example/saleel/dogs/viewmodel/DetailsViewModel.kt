package com.example.saleel.dogs.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.saleel.dogs.model.DogBread
import com.example.saleel.dogs.model.DogDatabase
import kotlinx.coroutines.launch

class DetailsViewModel(application: Application) : BaseViewModel(application) {
    //Base API =https://raw.githubusercontent.com/DevTides/DogsApi/master/dogs.json
    val dogsList = MutableLiveData<DogBread> ()
    fun fetch(uuid:Int){
        //val dog1 = DogBread("1000","saleel","10","A","BeadFor1","40","")
        retriveTheSingleDogDetails(uuid)
        //dogsList.value = retriveTheSingleDogDetails(uuid)
    }


    private fun retriveTheSingleDogDetails(uuid:Int){
        launch {
           val dog = DogDatabase(getApplication()).dogDao().getDogDetails(uuid)
            updateTheDogDetailsList(dog)
        }

    }

    private fun updateTheDogDetailsList(dog: DogBread) {
        dogsList.value = dog
    }
}