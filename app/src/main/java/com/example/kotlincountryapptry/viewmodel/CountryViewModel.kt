package com.example.kotlincountryapptry.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kotlincountryapptry.model.Country
import com.example.kotlincountryapptry.service.APIService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class CountryViewModel: ViewModel() {

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
                       countries.value=t
                        countryError.value=false
                        countryLoading.value=false
                    }

                    override fun onError(e: Throwable) {
                        countryError.value=true
                        countryLoading.value=false

                    }

                })
        )
    }
}