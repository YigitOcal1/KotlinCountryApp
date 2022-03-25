package com.example.kotlincountryapptry.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kotlincountryapptry.model.Country

class DetailViewModel : ViewModel() {


val detailLiveData=MutableLiveData<Country>()

    fun getDataFromRoom(){
        val country=Country(" qwe","qew","adasc","","","")
        detailLiveData.value=country
    }

}