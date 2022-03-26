package com.example.kotlincountryapptry.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kotlincountryapptry.model.Country
import com.example.kotlincountryapptry.service.APIService
import com.example.kotlincountryapptry.service.CountryDatabase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.launch

class CountryViewModel(application: Application): BaseViewModel(application) {

    private val APIService=APIService()
    private val disposable=CompositeDisposable()

    val countries=MutableLiveData<List<Country>>()
    val countryError=MutableLiveData<Boolean>()
    val countryLoading=MutableLiveData<Boolean>()

    fun refreshData(){
getDataFromAPI()
    }

    private fun getDataFromAPI(){
countryLoading.value=true

        disposable.add(

            APIService.getData().subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object :DisposableSingleObserver<List<Country>>(){
                    override fun onSuccess(t: List<Country>) {

                    }

                    override fun onError(e: Throwable) {
                        countryError.value=true
                        countryLoading.value=false

                    }

                })
        )
    }

    private fun showCountries(countryList:List<Country>){
        countries.value=countryList
        countryError.value=false
        countryLoading.value=false
    }
    @InternalCoroutinesApi
    private fun storeInSQLite(list: List<Country>){
        launch {
            val dao=CountryDatabase(getApplication()).countryDao()
            dao.deleteAllCountries()
           val listLong= dao.insertAll(*list.toTypedArray())  //tekli hale getiriyor
        var i=0
            while(i<list.size){
                list[i].uuid=listLong[i].toInt()
                i+=1
            }
            showCountries(list)
        }
    }
}