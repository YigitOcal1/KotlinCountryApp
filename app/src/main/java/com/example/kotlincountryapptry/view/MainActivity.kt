package com.example.kotlincountryapptry.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.kotlincountryapptry.R

class MainActivity : AppCompatActivity() {
    private lateinit var navigationController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navigationController=Navigation.findNavController(activity = this,R.id.fragment)

    }
}