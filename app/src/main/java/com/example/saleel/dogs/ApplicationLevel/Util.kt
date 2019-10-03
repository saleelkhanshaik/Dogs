package com.example.saleel.dogs.ApplicationLevel

import android.content.Context
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.saleel.dogs.R

val PERMISSION_SEND_SMS = 320
val FIREBASEID="1:571989701075:android:4ba8ea4abd5872db873f26"
val FIREBASEKEY="AIzaSyDsH2Ik3UbW5UtIisbMflMOqQIg_AtM2FA"

fun getProgressDrawable(context: Context):CircularProgressDrawable{
    return CircularProgressDrawable(context).apply {
        strokeWidth=10f
        centerRadius = 50f
        start()
    }
}
fun ImageView.loadImageView(url:String?,progressDrawable: CircularProgressDrawable){
    val option =RequestOptions()
                .placeholder(progressDrawable)
                .error(R.mipmap.ic_dog)

        Glide.with(context)
            .setDefaultRequestOptions(option)
            .load(url)
            .into(this)
}
@BindingAdapter("android:imageUrl")
fun loadImageUrl(view: ImageView,url:String?){
        view.loadImageView(url, getProgressDrawable(view.context))
}
