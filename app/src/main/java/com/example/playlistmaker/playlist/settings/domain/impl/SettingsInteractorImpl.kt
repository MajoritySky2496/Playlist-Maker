package com.example.playlistmaker.playlist.domain.settingsDomain.impl

import com.example.playlistmaker.playlist.settings.data.impl.SettingsRepositoryImpl
import com.example.playlistmaker.playlist.settings.domain.SettingsInteractor
import com.example.playlistmaker.playlist.settings.domain.api.SettingsRepository

class SettingsInteractorImpl(private val prefs: SettingsRepository):SettingsInteractor {
    override fun getThemeSettings(): Boolean {
        return prefs.getThemeSettings()
    }

    override fun updateThemeSettings(key: String, theme: Boolean) {
        prefs.updateThemeSettings(key, theme)

    }

}