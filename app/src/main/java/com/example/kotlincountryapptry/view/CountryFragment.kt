package com.example.kotlincountryapptry.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlincountryapptry.R
import com.example.kotlincountryapptry.adapter.CountryAdapter
import com.example.kotlincountryapptry.viewmodel.CountryViewModel
import kotlinx.android.synthetic.main.fragment_country.*
import kotlinx.coroutines.InternalCoroutinesApi


@InternalCoroutinesApi
class CountryFragment : Fragment() {

    private lateinit var viewModel:CountryViewModel
    private val countryAdapter=CountryAdapter(ArrayList())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_country, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //viewModel= ViewModelProviders.of(this).get(CountryViewModel::class.java)
        viewModel=ViewModelProvider(this).get(CountryViewModel::class.java)
        viewModel.refreshData()
        countryList.layoutManager=LinearLayoutManager(context)
        countryList.adapter=countryAdapter



/*
        val button=view.findViewById<Button>(R.id.button_fragment)
        button.setOnClickListener {
            val action=CountryFragmentDirections.actionCountryFragmentToDetailFragment()
        Navigation.findNavController(it).navigate(action)}*/

        swipeRefreshLayout.setOnRefreshListener {
            countryList.visibility=View.GONE
            countyError.visibility=View.GONE
            countryLoading.visibility=View.VISIBLE
            viewModel.refreshFromAPI()
            swipeRefreshLayout.isRefreshing=false

        }

        observerLiveData()
    }
private fun observerLiveData(){
    viewModel.countries.observe(viewLifecycleOwner, Observer {countries->
        countries?.let {
            countryList.visibility=View.VISIBLE
            countryAdapter.updateCountryList(countries)
        }
    })
    viewModel.countryError.observe(viewLifecycleOwner, Observer { error->
        error?.let {
            if (it){
                countyError.visibility=View.VISIBLE
            }
            else{
                countyError.visibility=View.GONE
            }

        }
    })
    viewModel.countryLoading.observe(viewLifecycleOwner, Observer { loading->
        loading?.let{
            if (it){
                countryLoading.visibility=View.VISIBLE
                countyError.visibility=View.GONE
                countryList.visibility=View.GONE
            }
            else{
                countryLoading.visibility=View.GONE
            }
    }
    })
}

}

