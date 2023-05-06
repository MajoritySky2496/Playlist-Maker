package com.example.playlistmaker.playlist.settings.data

import com.example.playlistmaker.playlist.domain.settingsDomain.model.ThemeSettings

interface SettingsRepository {
    fun getThemeSettings():ThemeSettings
    fun updateThemeSettings(settings: ThemeSettings)
}