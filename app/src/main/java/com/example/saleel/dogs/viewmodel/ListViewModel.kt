package com.example.saleel.dogs.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.saleel.dogs.model.DogBread
import com.example.saleel.dogs.model.DogsApiService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class ListViewModel : ViewModel() {
    private val disposable=CompositeDisposable()
    private val apiService=DogsApiService()

    val dogsList = MutableLiveData<List<DogBread>> ()
    val isLoading = MutableLiveData<Boolean> ()
    val errorMessage = MutableLiveData<Boolean>()
    fun refreshLis(){
        fetchDatFromServer()
    }
    private fun fetchDatFromServer(){
    isLoading.value = true
        disposable.add(apiService.getDogs()
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableSingleObserver<List<DogBread>>(){
                override fun onSuccess(t: List<DogBread>) {
                    isLoading.value = false
                    errorMessage.value = false
                    dogsList.value = t
                }
                override fun onError(e: Throwable) {
                    errorMessage.value = true
                    isLoading.value = false
                    e.printStackTrace()
                }
            }))
    }

}