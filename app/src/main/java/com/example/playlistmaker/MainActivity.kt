package com.example.playlistmaker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val search_button = findViewById<Button>(R.id.search_button)
        val mediateca_button = findViewById<Button>(R.id.mediateca_button)
        val setting_button = findViewById<Button>(R.id.settings_button)

        search_button.setOnClickListener{
            val displayIntent = Intent(this, SearchActivity::class.java)
            startActivity(displayIntent)
        }
        mediateca_button.setOnClickListener{
            val displayIntent = Intent(this, MediatecaActivity::class.java)
            startActivity(displayIntent)
        }
        setting_button.setOnClickListener{
            val displayIntent = Intent(this, SettingsActivity::class.java)
            startActivity(displayIntent)
        }
    }
}