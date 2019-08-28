package com.example.saleel.dogs.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.saleel.dogs.model.DogBread

class ListViewModel : ViewModel() {
    val dogsList = MutableLiveData<ArrayList<DogBread>> ()
    val isLoading = MutableLiveData<Boolean> ()
    val errorMessage = MutableLiveData<Boolean>()
    fun refreshLis(){
        val dog1 =DogBread("100","DogBread1","10","A","BeadFor1","40","")
        val dog2 =DogBread("101","DogBread2","11","B","BeadFor2","41","")
        val dog3 =DogBread("102","DogBread3","12","C","BeadFor3","42","")
        val dogList  = arrayListOf<DogBread>(dog1,dog2,dog3)
        dogsList.value = dogList
        isLoading.value = false
        errorMessage.value = false
    }

}