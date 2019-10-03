package com.example.saleel.dogs.ApplicationLevel

import android.app.Application
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions

class Appstate:Application() {
    override fun onCreate() {
        super.onCreate()


      var  options =  FirebaseOptions.Builder()
            .setApplicationId(FIREBASEID) // Required for Analytics.
            .setApiKey(FIREBASEKEY) // Required for Auth.

            .build();

        FirebaseApp.initializeApp(this, options, "Dogs");
    }
}