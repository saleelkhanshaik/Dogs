package com.example.saleel.dogs.model

import io.reactivex.Single
import retrofit2.http.GET

interface DogAPI {
    @GET("DevTides/DogsApi/master/dogs.json")
    fun getDogs(): Single<List<DogBread>>
    //if we have multiple end points will set here
    @GET("endpoints2")
    fun getDogsListTwo():Single<List<DogBread>>
}