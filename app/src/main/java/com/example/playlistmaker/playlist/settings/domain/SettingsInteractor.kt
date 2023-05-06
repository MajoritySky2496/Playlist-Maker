package com.example.playlistmaker.playlist.settings.domain

import com.example.playlistmaker.playlist.domain.settingsDomain.model.ThemeSettings

interface SettingsInteractor {
    fun getThemeSettings(): ThemeSettings
    fun updateThemeSettings(settings: ThemeSettings)

}