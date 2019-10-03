package com.example.saleel.dogs.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class DogBread(
    @ColumnInfo(name = "bread_id")
    @SerializedName("id")
    val breadID:String?,

    @ColumnInfo(name =  "braed_name")
    @SerializedName("name")
    val dogBread:String?,

    @ColumnInfo(name = "bread_life_spam")
    @SerializedName("life_span")
    val lifeSpam:String?,

    @ColumnInfo(name = "bread_group")
    @SerializedName("breed_group")
    val breadGroup:String?,

    @ColumnInfo(name =  "bread_for")
    @SerializedName("bred_for")
    val breadFor:String?,

    @SerializedName("temperament")
    val temparment:String?,

    @ColumnInfo(name = "dog_url")
    @SerializedName("url")
    val dogImageURL:String?
){
    @PrimaryKey(autoGenerate = true)
    var uuid :Int = 0
}
data class DogPallate(var color:Int)
data class SmsInfo(
    var to :String,
    var text:String,
    var imageURL:String

)