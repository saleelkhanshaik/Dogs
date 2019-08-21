package com.example.saleel.dogs.view.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavDirections
import androidx.navigation.Navigation

import com.example.saleel.dogs.R
import kotlinx.android.synthetic.main.fragment_dog_deatils.*
import kotlinx.android.synthetic.main.fragment_dog_list_main.*


class DogDeatils : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dog_deatils, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        detailButton.setOnClickListener {
            //here will call the Dogs lis fragment with help of navHost and NavContoller
            val action:NavDirections = DogDeatilsDirections.actionDogDeatilsToDogListMain()

            Navigation.findNavController(it).navigate(action)
        }
    }
}
