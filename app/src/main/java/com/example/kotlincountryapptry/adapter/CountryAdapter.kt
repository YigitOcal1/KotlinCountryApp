package com.example.kotlincountryapptry.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlincountryapptry.R
import com.example.kotlincountryapptry.model.Country
import com.example.kotlincountryapptry.view.CountryFragmentDirections
import kotlinx.android.synthetic.main.item_country.view.*
import org.w3c.dom.Text

class CountryAdapter(val countryList:ArrayList<Country>): RecyclerView.Adapter<CountryAdapter.CountryViewHolder>() {







    class CountryViewHolder(var view: View) : RecyclerView.ViewHolder(view) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        val inflater=LayoutInflater.from(parent.context)
        val view =inflater.inflate(R.layout.item_country,parent,false)
        return CountryViewHolder(view)

    }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        holder.view.name.text = countryList[position].countryName

        holder.view.region.text = countryList[position].countryRegion

        holder.view.setOnClickListener {
            val action = CountryFragmentDirections.actionCountryFragmentToDetailFragment()
            Navigation.findNavController(it).navigate(action)
        }
    }
    override fun getItemCount(): Int {
       return countryList.size
    }
    fun updateCountryList(newCountryList:List<Country>){
        countryList.clear()
        countryList.addAll(newCountryList)
        notifyDataSetChanged()
    }
}