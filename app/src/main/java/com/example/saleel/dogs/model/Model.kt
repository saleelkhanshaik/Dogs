package com.example.saleel.dogs.model

import com.google.gson.annotations.SerializedName

data class DogBread(
    @SerializedName("id")
    val breadID:String?,
    @SerializedName("name")
    val dogBread:String?,
    @SerializedName("life_span")
    val lifeSpam:String?,
    @SerializedName("breed_group")
    val breadGroup:String?,
    @SerializedName("bred_for")
    val breadFor:String?,
    @SerializedName("temperament")
    val temparment:String?,
    @SerializedName("url")
    val dogImageURL:String?
)