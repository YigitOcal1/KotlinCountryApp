package com.example.kotlincountryapptry.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlincountryapptry.R
import com.example.kotlincountryapptry.databinding.ItemCountryBinding
import com.example.kotlincountryapptry.model.Country
import com.example.kotlincountryapptry.util.myImageFunction
import com.example.kotlincountryapptry.util.placeholderProgressBar
import com.example.kotlincountryapptry.view.CountryFragmentDirections
import kotlinx.android.synthetic.main.item_country.view.*
import org.w3c.dom.Text

class CountryAdapter(val countryList:ArrayList<Country>): RecyclerView.Adapter<CountryAdapter.CountryViewHolder>(),CountryClickListener {



    class CountryViewHolder(var view: ItemCountryBinding) : RecyclerView.ViewHolder(view.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        val inflater=LayoutInflater.from(parent.context)
        //val view =inflater.inflate(R.layout.item_country,parent,false)
        val view=DataBindingUtil.inflate<ItemCountryBinding>(inflater,R.layout.item_country,parent,false)
        return CountryViewHolder(view)

    }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {


        holder.view.country=countryList[position]
        holder.view.listener=this





        /*
        holder.view.name.text = countryList[position].countryName
        holder.view.region.text = countryList[position].countryRegion
        holder.view.setOnClickListener {
            val action = CountryFragmentDirections.actionCountryFragmentToDetailFragment(countryUuid = countryList[position].uuid)
            println("qweqwefawsefwdenemmem"+countryList[position].uuid)
            Navigation.findNavController(it).navigate(action)

        }
        holder.view.imageView.myImageFunction(countryList[position].imageUrl, placeholderProgressBar(holder.view.context))*/
    }
    override fun getItemCount(): Int {
       return countryList.size
    }
    fun updateCountryList(newCountryList:List<Country>){
        countryList.clear()
        countryList.addAll(newCountryList)
        notifyDataSetChanged()
    }

    override fun onCountryClicked(view: View) {
        val uuid=view.countryUuidText.text.toString().toInt()
        val action = CountryFragmentDirections.actionCountryFragmentToDetailFragment(uuid)

        Navigation.findNavController(view).navigate(action)

    }
}