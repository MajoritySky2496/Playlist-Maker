package com.example.playlistmaker.playlist.main.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.playlistmaker.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class RootActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_root)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.container_view) as NavHostFragment
        val navController = navHostFragment.navController

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNavigationView.setupWithNavController(navController)

//        bottomNavigationView.setOnItemReselectedListener { item ->
//            when(item.itemId){
//                R.id.searchFragment -> {
//                    bottomNavigationView.itemTextColor = ContextCompat.getColorStateList(this, R.color.blue)
//
//                }
//            }
//        }

//        val searchButton = findViewById<Button>(R.id.search_button)
//        val mediatecaButton = findViewById<Button>(R.id.mediateca_button)
//        val settingButton = findViewById<Button>(R.id.settings_button)
//
//        searchButton.setOnClickListener{
//            val displayIntent = Intent(this, SearchActivity::class.java)
//            startActivity(displayIntent)
//        }
//        mediatecaButton.setOnClickListener{
//            val displayIntent = Intent(this, MediatecaActivity::class.java)
//            startActivity(displayIntent)
//        }
//        settingButton.setOnClickListener{
//            val displayIntent = Intent(this, SettingTrackActivity::class.java)
//            startActivity(displayIntent)
//        }
    }
}