package com.example.playlistmaker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

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
            val displayIntent = Intent(this, SettingsActivity::class.java)
            startActivity(displayIntent)
        }
    }
}