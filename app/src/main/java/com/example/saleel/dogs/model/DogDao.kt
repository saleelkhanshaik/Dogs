package com.example.saleel.dogs.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

/*
* data access class where we can insert and retrive the data
* here DogBread is Model class (and the table name also same)*/
@Dao
interface DogDao {
    //to insert the data to database
    @Insert
    suspend fun insertDog(vararg dogBread: DogBread):List<Long>
    //to get the all the details about table
    @Query("SELECT * FROM DogBread")
    suspend fun getAllDogDetails():List<DogBread>
    //to get the single dog details,here we are passing the uuid value
    @Query("SELECT * FROM DogBread WHERE uuid = :bread_id")
    suspend fun getDogDetails(bread_id : Int):DogBread

    @Query("DELETE FROM DogBread")
    suspend fun deteleDogTable()

}