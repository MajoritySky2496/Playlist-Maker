package com.example.playlistmaker.playlist.settings.ui

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.playlistmaker.R
import com.example.playlistmaker.playlist.settings.presentation.SettingsViewModel
import com.example.playlistmaker.playlist.settings.ui.model.SwitcherState
import com.example.playlistmaker.playlist.util.NavigationRouter
import com.google.android.material.switchmaterial.SwitchMaterial

class SettingTrackActivity : AppCompatActivity() {

    lateinit var themeSwitcher: SwitchMaterial
    lateinit var shareButton: TextView
    lateinit var writeToSupportButton: TextView
    lateinit var userAgreementbutton: TextView
    lateinit var backButton: ImageView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        val viewModel by lazy {
            ViewModelProvider(
                this, SettingsViewModel.getViewModelFactory(this)
            )[SettingsViewModel::class.java]
        }
        initView()
        if (
            viewModel.getThemeSettings() == true) {
            themeSwitcher.toggle()
        }
        viewModel.getSwitcherState().observe(this) { switcherToggel(it) }
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

    fun switcherToggel(state: SwitcherState) {
        when (state) {
            is SwitcherState.toggle -> switcherTheme(state.theme)
        }
    }

    fun switcherTheme(theme: Boolean) {
        if (theme == true) {
            themeSwitcher.toggle()
        }
        return
    }


}