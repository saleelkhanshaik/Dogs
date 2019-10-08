package com.example.saleel.dogs.view.fragments


import android.app.PendingIntent
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.telephony.SmsManager
import android.util.Log
import android.view.*
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import androidx.palette.graphics.Palette
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.saleel.dogs.ApplicationLevel.getProgressDrawable
import com.example.saleel.dogs.ApplicationLevel.loadImageView

import com.example.saleel.dogs.R
import com.example.saleel.dogs.databinding.FragmentDogDeatilsBinding
import com.example.saleel.dogs.databinding.SendsmldialogBinding
import com.example.saleel.dogs.model.DogBread
import com.example.saleel.dogs.model.DogPallate
import com.example.saleel.dogs.model.SmsInfo
import com.example.saleel.dogs.view.DogClickListener
import com.example.saleel.dogs.view.MainActivity
import com.example.saleel.dogs.viewmodel.DetailsViewModel
import kotlinx.android.synthetic.main.fragment_dog_deatils.*
import kotlinx.android.synthetic.main.fragment_dog_deatils.view.*
import kotlinx.android.synthetic.main.fragment_dog_deatils.view.button1
import java.util.jar.Manifest


class DogDeatils : Fragment(),DogClickListener {
     var sendSMSstarted :Boolean= false
    var i = 1
    private var currentDog:DogBread? =  null
    override fun onDogClicked(view: View) {
      //  setupBackground(view.dummyURL.text.toString())
        if(i <= 6){
            setupBackground1(dummyURL.text.toString(),i)
            i++;
        }else{
            i = 1
        }
    }
    private var sendSmsStarted = false
    private lateinit var viewModel:DetailsViewModel
    //for binding
    private lateinit var dataBinding : FragmentDogDeatilsBinding
    val args : DogDeatilsArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        dataBinding =DataBindingUtil.inflate(inflater,R.layout.fragment_dog_deatils,container,false)
       // return inflater.inflate(R.layout.fragment_dog_deatils, container, false)
        setHasOptionsMenu(true)
        return dataBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(DetailsViewModel::class.java)
       // viewModel.fetch()
        val uuid = args.doguuid
        if(uuid != null ){
            viewModel.fetch(uuid)
        }


        obserViewModel()
    }
    fun obserViewModel(){
        viewModel.dogsList.observe(this, Observer {

            dogdata->
            currentDog = dogdata
            dogdata?.let {

            dataBinding.dogdetails = dogdata
            dataBinding.dogclickevent = this
            //dogdetails
//            dogName.text = dog.dogBread
//            dogpurpose.text = dog.breadFor
//            dogtemparment.text = dog.temparment
//            doglifespam.text = dog.lifeSpam
//            dogImageView.loadImageView(dog.dogImageURL, getProgressDrawable(dogImageView.context))

            it.dogImageURL?.let {
                setupBackground(it)
            }
        }
        })
    }

    private fun setupBackground(it: String) {
        Glide.with(this).asBitmap()
            .load(it).into(object :CustomTarget<Bitmap>(){
                override fun onLoadCleared(placeholder: Drawable?) {

                }

                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    Palette.from(resource).generate { palette ->
                        var intColor = 0;

                        intColor = palette?.vibrantSwatch?.rgb?: 0
                        val myPallete = DogPallate(intColor)
                        dataBinding.dogpallete = myPallete
                    }
                }
            })

    }
    private fun setupBackground1(it: String,check:Int) {
        Glide.with(this).asBitmap()
            .load(it).into(object :CustomTarget<Bitmap>(){
                override fun onLoadCleared(placeholder: Drawable?) {

                }

                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    Palette.from(resource).generate { palette ->
                        var intColor = 0;
                        when (check){
                            1 -> {
                                intColor = palette?.vibrantSwatch?.rgb?: 0
                            }
                            2 -> {
                                intColor = palette?.darkVibrantSwatch?.rgb?: 0
                            }
                            3 ->{
                                intColor = palette?.lightVibrantSwatch?.rgb?: 0
                            }
                            4 ->{
                                intColor = palette?.mutedSwatch?.rgb?: 0
                            }
                            5 ->{
                                intColor = palette?.darkMutedSwatch?.rgb?: 0
                            }
                            6 ->{
                                intColor = palette?.lightMutedSwatch?.rgb?: 0
                            }
                        }

                        val myPallete = DogPallate(intColor)
                        dataBinding.dogpallete = myPallete
                    }
                }
            })

    }



    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.details_menu,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.sendsms -> {
                sendSMSstarted = true
                (activity as MainActivity).checkSmsPermission()
            }
            R.id.shareoption -> {
                val intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    type = "text/plain"

                    putExtra(Intent.EXTRA_SUBJECT, "Check out this dog bread")
                    putExtra(
                        Intent.EXTRA_TEXT,
                        "${currentDog?.dogBread} bred for ${currentDog?.breadFor}"
                    )
                    putExtra(Intent.EXTRA_STREAM, "" + currentDog?.dogImageURL)
                }
                startActivity(Intent.createChooser(intent, "Share with"))
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun onPermissionResult(permissionGranted:Boolean){
        if(sendSMSstarted && permissionGranted){
            context?.let {
                val smsInfo = SmsInfo("","${currentDog?.dogBread} bred for ${currentDog?.breadFor} ","${currentDog?.dogImageURL}")
                val dialogBinding=DataBindingUtil.
                    inflate<SendsmldialogBinding>(LayoutInflater.from(it),
                    R.layout.sendsmldialog,null,
                        false)
                AlertDialog.Builder(it).setView(dialogBinding.root)
                    .setPositiveButton("Send SMS"){dialogInterface, i ->
                        if(!dialogBinding.smsDestination.text.isNullOrEmpty()){
                            smsInfo.to = dialogBinding.smsDestination.text.toString()
                            sendSMS(smsInfo)
                        }
                    }.setNegativeButton("Cancel"){
                        dialogInterface, i ->{}
                    }.show()
                dialogBinding.smsInfo = smsInfo
            }


        }
    }

    private fun sendSMS(smsInfo: SmsInfo) {
      var URL=  (activity as MainActivity).fecthdetails()
        val intent = Intent(context,MainActivity::class.java)
        val pi= PendingIntent.getActivity(context,0,intent,0)
        val smsManager=SmsManager.getDefault()
        smsManager.sendTextMessage(smsInfo.to,null,smsInfo.text+"\n"+URL,pi,null)
    }


}
