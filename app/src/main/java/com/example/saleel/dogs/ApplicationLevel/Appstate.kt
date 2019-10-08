package com.example.saleel.dogs.ApplicationLevel

import android.app.Application
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions

class Appstate:Application() {
    override fun onCreate() {
        super.onCreate()


      var  options =  FirebaseOptions.Builder()
            .setApplicationId(FIREBASEID)
            .setApiKey(FIREBASEKEY)

            .build();

        FirebaseApp.initializeApp(this, options, "Dogs")
    }
}