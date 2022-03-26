package com.example.kotlincountryapptry.service

import com.example.kotlincountryapptry.model.Country
import io.reactivex.Single
import retrofit2.Call
import retrofit2.http.GET

interface API {

    @GET("**********")
    fun getCountries():Single<List<Country>>
}