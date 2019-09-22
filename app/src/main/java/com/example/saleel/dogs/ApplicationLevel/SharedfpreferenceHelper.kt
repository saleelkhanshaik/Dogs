package com.example.saleel.dogs.ApplicationLevel

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import androidx.core.content.edit
import java.util.concurrent.locks.Lock

class SharedfpreferenceHelper {
//we are we are creating the singleton class

    companion object {
        private const val PREF_TIME ="Pref_time"
        private var prefs:SharedPreferences? =null
        @Volatile private var instance:SharedfpreferenceHelper? =null
        private val Lock = Any()
        operator fun invoke(context: Context):SharedfpreferenceHelper = instance ?: synchronized(
            Lock){
            instance ?: builderHelper(context).also{
                instance = it
            }
        }
        private fun builderHelper(context: Context):SharedfpreferenceHelper{
            prefs = PreferenceManager.getDefaultSharedPreferences(context)
            return SharedfpreferenceHelper()
        }
    }
    fun sharedUpdateTime(long: Long){
        prefs?.edit(commit = true){
            putLong(PREF_TIME,long)
        }
    }

    fun getLatsUpdatedData() =
        prefs?.getLong(PREF_TIME,0)
    fun getCacheDurationTime() = prefs?.getString("pref_cache_duration","")

}