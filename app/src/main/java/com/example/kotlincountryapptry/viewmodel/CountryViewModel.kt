package com.example.kotlincountryapptry.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kotlincountryapptry.model.Country
import com.example.kotlincountryapptry.service.APIService
import com.example.kotlincountryapptry.service.CountryDatabase
import com.example.kotlincountryapptry.util.CustomSharedPreferences
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.launch
import kotlin.math.E

class CountryViewModel(application: Application): BaseViewModel(application) {

    private val APIService=APIService()
    private val disposable=CompositeDisposable()
    @InternalCoroutinesApi
    private var customPreferences=CustomSharedPreferences(getApplication())

    private var refreshTime=10*60*1000*1000*1000L

    val countries=MutableLiveData<List<Country>>()
    val countryError=MutableLiveData<Boolean>()
    val countryLoading=MutableLiveData<Boolean>()

    @InternalCoroutinesApi
    fun refreshData(){

        val updateTime=customPreferences.getTime()
        if(updateTime!=null && updateTime!=0L && System.nanoTime()-updateTime<refreshTime){
            getDataFromSQLite()
        }
        else{
            getDataFromAPI()
        }

        
    }
    @InternalCoroutinesApi
    fun refreshFromAPI(){
        getDataFromAPI()
    }
    @InternalCoroutinesApi
    private fun getDataFromSQLite(){
        countryLoading.value=true
        launch {
            val countries=CountryDatabase(getApplication()).countryDao().getAllCountries()
            showCountries(countries)
            Toast.makeText(getApplication(),"countries from sqlite",Toast.LENGTH_LONG).show()
        }
    }
    @InternalCoroutinesApi
    private fun getDataFromAPI(){
countryLoading.value=true

        disposable.add(

            APIService.getData().subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object :DisposableSingleObserver<List<Country>>(){
                    override fun onSuccess(t: List<Country>) {
                        storeInSQLite(t)
                        Toast.makeText(getApplication(),"countries from api",Toast.LENGTH_LONG).show()
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
                i += 1
            }
            showCountries(list)
        }

        customPreferences.saveTime(System.nanoTime())

    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}