package com.example.saleel.dogs.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.saleel.dogs.ApplicationLevel.NotificationHelper
import com.example.saleel.dogs.ApplicationLevel.SharedfpreferenceHelper
import com.example.saleel.dogs.model.DogBread
import com.example.saleel.dogs.model.DogDatabase
import com.example.saleel.dogs.model.DogsApiService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch

class ListViewModel(application: Application) : BaseViewModel(application) {
    private var sharedpreferenceHelper=SharedfpreferenceHelper(getApplication())
    private val disposable=CompositeDisposable()
    private val apiService=DogsApiService()
    private var timeDelay :Long = 5* 50 * 1000 * 1000 * 1000L
    val dogsList = MutableLiveData<List<DogBread>> ()
    val isLoading = MutableLiveData<Boolean> ()
    val errorMessage = MutableLiveData<Boolean>()
    fun refreshLis(){
        val updatedTime:Long? =sharedpreferenceHelper.getLatsUpdatedData()
        if(updatedTime != null && updatedTime != 0L && (System.nanoTime() - updatedTime) < timeDelay)
        {
            fetchDatFromDatabase()
        }else{
            fetchDatFromServer()
        }

    }

    private fun fetchDatFromDatabase() {
        isLoading.value = true
        launch {
            val dogdabase = DogDatabase(getApplication()).dogDao().getAllDogDetails()
            dogRetrived(dogdabase)
            Toast.makeText(getApplication(),"Dogs Retrived from Database",Toast.LENGTH_SHORT).show()
        }

    }

    private fun fetchDatFromServer(){
    isLoading.value = true
        disposable.add(apiService.getDogs()
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableSingleObserver<List<DogBread>>(){
                override fun onSuccess(t: List<DogBread>) {
                   //dogRetrived(t)
                    dogStoreLocally(t)
                    Toast.makeText(getApplication(),"Dogs Retrived from Server",Toast.LENGTH_SHORT).show()
                    NotificationHelper(getApplication()).createNotification()
                }
                override fun onError(e: Throwable) {
                    errorMessage.value = true
                    isLoading.value = false
                    e.printStackTrace()
                }
            }))
    }

    private fun dogRetrived(list:List<DogBread>){
        isLoading.value = false
        errorMessage.value = false
        dogsList.value = list
    }
    private fun dogStoreLocally(list: List<DogBread>){
        launch {
            //deleting the old database
            val dao = DogDatabase(getApplication()).dogDao()
            dao.deleteDogTable()
            val  result=dao.insertDog(*list.toTypedArray())
            var  i =0
            while (i<list.size){
                list[i].uuid = result[1].toInt()
                ++i
            }
            dogRetrived(list)
        }
        sharedpreferenceHelper.sharedUpdateTime(System.nanoTime())
    }

}