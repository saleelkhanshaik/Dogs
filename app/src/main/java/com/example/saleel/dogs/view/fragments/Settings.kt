package com.example.saleel.dogs.view.fragments


import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat

import com.example.saleel.dogs.R


class Settings : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preference_user,rootKey)
    }


}
