package com.example.playlistmaker.playlist.settings.ui

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.playlistmaker.R
import com.example.playlistmaker.playlist.search.presentation.TracksSearchViewModel
import com.example.playlistmaker.playlist.settings.presentation.SettingsViewModel
import com.example.playlistmaker.playlist.settings.ui.model.SwitcherState
import com.example.playlistmaker.playlist.util.NavigationRouter
import com.google.android.material.switchmaterial.SwitchMaterial
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class SettingTrackActivity : AppCompatActivity() {

    lateinit var themeSwitcher: SwitchMaterial
    lateinit var shareButton: TextView
    lateinit var writeToSupportButton: TextView
    lateinit var userAgreementbutton: TextView
    lateinit var backButton: ImageView
    val viewModel by viewModel<SettingsViewModel> ()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        initView()
        if (viewModel.getThemeSettings() == true) { themeSwitcher.toggle() }

        themeSwitcher.setOnCheckedChangeListener { switcher, checked ->
            viewModel.switchTheme(checked)

        }
        shareButton.setOnClickListener {
            viewModel.shareApp()
        }
        writeToSupportButton.setOnClickListener {
            viewModel.openSupport()
        }
        userAgreementbutton.setOnClickListener {
            viewModel.openTerms()
        }
        backButton.setOnClickListener {
            NavigationRouter().goBack(this)
        }
    }

    fun initView() {
        shareButton = findViewById<TextView>(R.id.share_button)
        writeToSupportButton = findViewById<TextView>(R.id.writeToSupport_button)
        userAgreementbutton = findViewById<TextView>(R.id.userAgreement_button)
        backButton = findViewById<ImageView>(R.id.back_button)
        themeSwitcher = findViewById(R.id.themeSwitcher)
    }




}