package com.example.saleel.dogs.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = arrayOf(DogBread::class),version = 1)
abstract  class DogDatabase:RoomDatabase(){
    abstract fun dogDao():DogDao

    companion object{
        @Volatile private  var  instance : DogDatabase? =null
        private val LoCK = Any()
        operator fun invoke(context: Context) = instance ?: synchronized(LoCK){
            instance ?: buildeDatabase(context).also{
                instance = it
            }
        }
        private fun buildeDatabase(context: Context)=Room.databaseBuilder(
            context.applicationContext,
            DogDatabase::class.java,
            "dogdatabase"
        ).build()
    }

}