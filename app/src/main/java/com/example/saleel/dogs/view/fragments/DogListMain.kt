package com.example.saleel.dogs.view.fragments


import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager

import com.example.saleel.dogs.R
import com.example.saleel.dogs.view.DogsListAdapter
import com.example.saleel.dogs.viewmodel.ListViewModel
import kotlinx.android.synthetic.main.fragment_dog_list_main.*


class DogListMain : Fragment() {
    private lateinit var viewModelDog:ListViewModel
    private val dogListAdapter =DogsListAdapter(arrayListOf())
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //to make optionitem visible
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_dog_list_main, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModelDog  = ViewModelProvider(this).get(ListViewModel::class.java)
        viewModelDog.refreshLis()
        dogsList.apply {
            layoutManager=LinearLayoutManager(context)
            //layoutManager =GridLayoutManager(context,2)
            adapter = dogListAdapter
        }
        observeViewModel()
        refreshLayout.setOnRefreshListener {
            dogsList.visibility = View.GONE
            loadingProgressbar.visibility = View.VISIBLE
            noData.visibility = View.GONE
            viewModelDog.refreshLis()
            refreshLayout.isRefreshing = false
        }
    }

    private fun observeViewModel() {
     viewModelDog.dogsList.observe(this, Observer {
         dogs -> dogs?.let {
         dogsList.visibility=View.VISIBLE
         dogListAdapter.updateDogList(dogs)}
     })
        viewModelDog.isLoading.observe(this, Observer {
            isLoading -> isLoading?.let {
            loadingProgressbar.visibility = if(it) View.VISIBLE else View.GONE
            if(it) {
                dogsList.visibility = View.GONE
                noData.visibility= View.GONE}else{

                dogsList.visibility = View.VISIBLE
                noData.visibility= View.VISIBLE
            }
        }
        })
        viewModelDog.errorMessage.observe(this, Observer {
            error -> error?.let {
            noData.visibility = if(it) View.VISIBLE else View.GONE   }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.list_menu,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.action_preference_settings ->{
                view?.let {
                    Navigation.findNavController(it).navigate(DogListMainDirections.actionDogListMainToSettings())
                }
            }
        }
        return super.onOptionsItemSelected(item)

    }
}
