package com.example.playlistmaker.playlist.main.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.playlistmaker.playlist.mediateca.ui.activity.MediatecaActivity
import com.example.playlistmaker.R
import com.example.playlistmaker.playlist.search.ui.tracks.SearchActivity
import com.example.playlistmaker.playlist.settings.ui.SettingTrackActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val searchButton = findViewById<Button>(R.id.search_button)
        val mediatecaButton = findViewById<Button>(R.id.mediateca_button)
        val settingButton = findViewById<Button>(R.id.settings_button)

        searchButton.setOnClickListener{
            val displayIntent = Intent(this, SearchActivity::class.java)
            startActivity(displayIntent)
        }
        mediatecaButton.setOnClickListener{
            val displayIntent = Intent(this, MediatecaActivity::class.java)
            startActivity(displayIntent)
        }
        settingButton.setOnClickListener{
            val displayIntent = Intent(this, SettingTrackActivity::class.java)
            startActivity(displayIntent)
        }
    }
}