package com.example.kotlincountryapptry.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kotlincountryapptry.model.Country
import com.example.kotlincountryapptry.service.CountryDatabase
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.launch

class DetailViewModel (application: Application): BaseViewModel(application) {


val detailLiveData=MutableLiveData<Country>()

    @InternalCoroutinesApi
    fun getDataFromRoom(uuid:Int){

        launch {

            val dao=CountryDatabase(getApplication()).countryDao()
            val country=dao.getCountry(uuid)
            detailLiveData.value=country
        }
    }

}