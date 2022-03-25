package com.example.kotlincountryapptry.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kotlincountryapptry.model.Country

class CountryViewModel: ViewModel() {
    val countries=MutableLiveData<List<Country>>()
    val countryError=MutableLiveData<Boolean>()
    val countryLoading=MutableLiveData<Boolean>()

    fun refreshData(){
        val country=Country("kars","qwewqe","wqewq","xczvzxcv","qwfwafws","www.qdqw.com")
        val country2=Country("kars2","qwewqe","wqewq","xczvzxcv","qwfwafws","www.qdqw.com")
        val country3=Country("kars3","qwewqe","wqewq","xczvzxcv","qwfwafws","www.qdqw.com")

        val countryList= arrayListOf<Country>(country,country2,country3)
        countries.value=countryList
        countryError.value=false
        countryLoading.value=false

    }
}