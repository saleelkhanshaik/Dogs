package com.example.saleel.dogs.view

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.example.saleel.dogs.ApplicationLevel.PERMISSION_SEND_SMS
import com.example.saleel.dogs.R
import com.example.saleel.dogs.view.fragments.DogDeatils
import com.google.firebase.FirebaseApp
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController
     lateinit var mFirebaseRemoteConfig: FirebaseRemoteConfig
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //FirebaseApp.initializeApp(this)
        mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance()
        val configSetting =FirebaseRemoteConfigSettings.Builder()
            .setMinimumFetchIntervalInSeconds(3600)

            .build()
        mFirebaseRemoteConfig.setConfigSettingsAsync(configSetting)
        mFirebaseRemoteConfig.setDefaultsAsync(R.xml.config_defaults)
        navController = Navigation.findNavController(this, R.id.navHostMainFrag)
        NavigationUI.setupActionBarWithNavController(this, navController)
    }
    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController, null)
    }

    fun checkSmsPermission() {
        if(ContextCompat.checkSelfPermission(this,android.Manifest.permission.SEND_SMS) !=
            PackageManager.PERMISSION_GRANTED){
            if(ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.SEND_SMS)){
                AlertDialog.Builder(this).setTitle("Send SMS permission")
                    .setMessage("This app requires access to send an SMS")
                    .setPositiveButton("Ask me"){
                        dialog, which -> requestPermission()
                    }
                    .setNegativeButton("Not now"){
                        dialog, which ->  notifyDetailFragment(false)
                    }.show()
            }else{
                requestPermission()
            }
        }else{
            notifyDetailFragment(true)
        }
    }

    private fun notifyDetailFragment(permissionGranted: Boolean) {
        val activeFragment = navHostMainFrag.childFragmentManager.primaryNavigationFragment
        if(activeFragment is DogDeatils) {
            (activeFragment).onPermissionResult(permissionGranted)
        }
    }

    private fun requestPermission(){
        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.SEND_SMS),PERMISSION_SEND_SMS)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when(requestCode){
            PERMISSION_SEND_SMS ->{
                if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    notifyDetailFragment(true)
                }else{
                    notifyDetailFragment(false)
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

}
