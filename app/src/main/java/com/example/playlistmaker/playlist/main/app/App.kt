package com.example.playlistmaker.playlist.main.app

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.example.playlistmaker.playlist.creator.Creator
import com.example.playlistmaker.playlist.settings.domain.SettingsInteractor

class App:Application() {
    lateinit var settingInteractor:SettingsInteractor
    var darkTheme = false
    override fun onCreate() {
        super.onCreate()

        settingInteractor= Creator.provideSettingInteractor(this)
        switchTheme(settingInteractor.getThemeSettings())
    }

    fun switchTheme(darkThemeEnabled: Boolean) {
        darkTheme = darkThemeEnabled
        AppCompatDelegate.setDefaultNightMode(
            if (darkThemeEnabled) {
                AppCompatDelegate.MODE_NIGHT_YES
            } else {
                AppCompatDelegate.MODE_NIGHT_NO
            }
        )
    }
}