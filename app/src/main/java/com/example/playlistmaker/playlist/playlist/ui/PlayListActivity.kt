package com.example.playlistmaker.playlist.playlist.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.example.playlistmaker.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class PlayListActivity:AppCompatActivity() {
    lateinit var bottomNavigationView: BottomNavigationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_playlist)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.container_view_playlist) as NavHostFragment
        val navController = navHostFragment.navController

    }
}